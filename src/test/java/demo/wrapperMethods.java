package demo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.databind.ObjectMapper;

public class wrapperMethods {

    WebDriver driver;  
    static WebDriverWait wait;
    static JavascriptExecutor js;

    public wrapperMethods(WebDriver driver) {
        this.driver = driver;
        js = (JavascriptExecutor) driver;        
        System.out.println("Constructor: wrapperMethods");
    }

    public void clickOnElement(WebElement elementToClick) {
        try {
            if (elementToClick != null && elementToClick.isDisplayed()) {
                // Initialize the webdriver wait
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                // Wait till the element is visible
                wait.until(ExpectedConditions.visibilityOf(elementToClick));
                elementToClick.click();
                Thread.sleep(3000);
                
            } else {
                System.out.println("Element is not displayed ");
            }
        } catch (Exception e) {
            System.out.println("Exception occurred while clicking: ");
            e.printStackTrace();
        }
    }
    
    public void jsonDataFile(ArrayList<HashMap<String, Object>> table_Data, String filePath) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            //root file path
            File file_Dir = new File("output");
            //checking for the folder/directory presence
            if(!file_Dir.exists()){
                file_Dir.mkdir();
            }
           
            File jsonFile = new File(file_Dir, filePath);
            //writing map data into json format
            mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, table_Data);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
