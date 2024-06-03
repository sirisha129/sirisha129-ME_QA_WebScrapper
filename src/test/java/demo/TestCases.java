package demo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCases {

    static ChromeDriver driver;
    static wrapperMethods utility;

    @BeforeSuite(alwaysRun = true)
    public static void driverSetUp() {
        System.out.println("Initializing : TestCases");
        WebDriverManager.chromedriver().timeout(30).setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

    }

    @BeforeClass
    public void setUp() {
        utility = new wrapperMethods(driver); // Initialize utility here after driver setup
    }

    @Test(priority = 1, description = "Collect Data of Hockey Teams: Forms, Searching and Pagination", enabled = true)

    public void scrapeHockey_JSON() throws InterruptedException {
        try {
            System.out.println("Start Test case:Collect Table Data into HashMap ");

            // ArrayList<MapInputs> table_Data = new ArrayList<>();
            ArrayList<HashMap<String, Object>> table_Data = new ArrayList<>();

            // using for loop and in the url pagenumber is iterating
            for (int pNum = 1; pNum <= 4; pNum++) {

                driver.get("https://www.scrapethissite.com/pages/forms/?page_num=" + pNum);
                Thread.sleep(3000);

                // gets the Table info
                WebElement table_Info = driver.findElement(By.xpath("//table[@class='table']"));
                // gets the rows info
                List<WebElement> table_Rows = table_Info.findElements(By.tagName("tr"));
                boolean row_Header = true;

                // ignoring the header part of the table
                for (WebElement rows : table_Rows) {
                    if (row_Header) {
                        row_Header = false;
                        continue;
                    }
                    // getting each row data from individual columns
                    List<WebElement> row_Cell = rows.findElements(By.tagName("td"));
                    if (row_Cell.size() > 0) {
                        String teamName = row_Cell.get(0).getText();
                        int year = Integer.parseInt(row_Cell.get(1).getText());
                        Double win_Percent = Double.parseDouble(row_Cell.get(5).getText());
                        
                        if (win_Percent < 0.40) {
                            long epoch_Time = System.currentTimeMillis() / 1000;

                            MapInputs inputs = new MapInputs(epoch_Time, teamName, year, win_Percent);

                            HashMap<String, Object> Map = new HashMap<>();

                            Map.put("EpochTime", inputs.getEpochTime());
                            Map.put("TeamName", inputs.getTeamNames());
                            Map.put("Year", inputs.getYear());
                            Map.put("WinPercent", inputs.getWinPercentage());
                            table_Data.add(Map);
                        }

                    }
                }

            }
            // creating json file with the collected ArrayList Data
            utility.jsonDataFile(table_Data, "hockey-team-data.json");

            File jsonFile = new File("output/hockey-team-data.json");

            // Assert that the JSON file is present and not empty
            Assert.assertTrue(jsonFile.exists(), "JSON file does not exist");
            Assert.assertTrue(jsonFile.length() > 0, "JSON file is empty");

            System.out.println("Successfully Printed Oscar JSON file location: " +jsonFile);
            System.out.println("End Test case: Scrape Hockey Team Forms and converted to JSON file");

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test case failed due to exception: " + e.getMessage());
        }      

    }

    @Test(priority = 2, description = "Collect Data of Oscar Winning Films", enabled = true)

    public void scrapeOscar_Json() throws InterruptedException {
        try {
            System.out.println("Start Test case: Scrape Oscar Winning Films");

            ArrayList<HashMap<String, Object>> movie_Data = new ArrayList<>();

            Thread.sleep(2000);
            driver.get("https://www.scrapethissite.com/pages/ajax-javascript/");
            Thread.sleep(2000);

            // Get the list of years
            List<WebElement> list_of_Years = driver.findElements(By.xpath("//div[@class='col-md-12 text-center']//a"));
            // click on each year
            for (WebElement yearLink : list_of_Years) {
                String year_Text = yearLink.getText();
                // storing year in a varaiable
                int year = Integer.parseInt(year_Text);
                //click on year link
                utility.clickOnElement(yearLink);
                // Get the table info
                WebElement movies_List = driver.findElement(By.xpath("//table[@class='table']"));
                // Get the row info
                List<WebElement> table_Rows = movies_List.findElements(By.tagName("tr"));

                boolean row_Header = true;
                int movieCount = 0;
                //to ignore row header
                for (WebElement row : table_Rows) {
                    if (row_Header) {
                        row_Header = false;
                        continue;
                    }
                    //once it reaches 5 moviecounts, rows loop should stop
                    if (movieCount >= 5) {
                        break;
                    }
                    //get the cell info
                    List<WebElement> row_Cell = row.findElements(By.tagName("td"));

                    if (row_Cell.size() > 0) {
                        String movie_Title = row_Cell.get(0).getText();                        
                        String nominations_Text = row_Cell.get(1).getText().trim();
                        String awards_Text = row_Cell.get(2).getText().trim();

                        int nominations = 0;
                        int awards = 0;
                        //to handle null string issue
                        if (nominations_Text!=null) {
                            nominations = Integer.parseInt(nominations_Text);
                        }
                        //to handle null string issue
                        if (awards_Text!=null) {
                            awards = Integer.parseInt(awards_Text);
                        }

                        boolean isWinner = movieCount == 0; // first movie is the best picture winner

                        long epochTime = System.currentTimeMillis() / 1000;

                        MapInputs inputs = new MapInputs(epochTime, year, movie_Title, nominations, awards,isWinner);
                        //mapinputs as object into this HashMap
                        HashMap<String, Object> dataMap = new HashMap<>();
                        dataMap.put("EpochTime", inputs.getEpochTime());
                        dataMap.put("Year", inputs.getYear());
                        dataMap.put("Title", inputs.getTitle());
                        dataMap.put("Nominations", inputs.getNomination());
                        dataMap.put("Awards", inputs.getAwards());
                        dataMap.put("isWinner", inputs.getisWinner());

                        movie_Data.add(dataMap);
                        movieCount++;
                    }
                }
                driver.navigate().back();
                Thread.sleep(2000);
            }

            // creating json file with the collected ArrayList Data
            utility.jsonDataFile(movie_Data, "oscar-winner-data.json");

            // Assert that the JSON file is present and not empty
            File jsonFile = new File("output/oscar-winner-data.json");

            Assert.assertTrue(jsonFile.exists(), "JSON file does not exist");
            
            Assert.assertTrue(jsonFile.length() > 0, "JSON file is empty.");

            System.out.println("Successfully Printed Oscar JSON file location: " +jsonFile);
            System.out.println("End Test case: Scrape Oscar Winning Films and converted to JSON file");

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test case failed due to exception: " + e.getMessage());
        }
    }

    @AfterSuite(alwaysRun = true)
    public void endTest() {
        System.out.println("Destroying Driver Instance");
        driver.close();
        driver.quit();
        System.out.println("Successfully Destroyed Driver");

    }

}
