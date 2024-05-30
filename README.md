# Project Name - sirisha129-ME_QA_WEBSCRAPPER

## Project Name and Description:
WebScraping Automation Suite , Scraping Data from websites and Storing in a JSON file format.

## Installation Instructions:
setUp a local Environment,for that you need 
1. IDE(of your choice,i used VSCode)
2. Java(Latest Version)
3. Gradle(Iam using a build tool called Gradle in my Project)
4. Git(Latest Version for clone,add,commit,push and change the code)
5. TestNG(get the Latest version from MVNrepository) and check the attachment below
6. Under Test Folder u need to Create resources Folder
7. Create testng.xml and provide your class path

> Example:
```
# java version 17 
java --version
# Gradle version 8.6
gradle --version
# Git 2.44
git --version
#VSCode 1.89.1
code --version
#TestNG 6.14.3
Add dependency into build.gradle
# JACKSON 2.14.2
Add dependency into build.gradle
```

## Usage and Examples:
Iam providing some scenarios to showcase how the project works.

1. Test Case 1: Scrapes hockey team data, filtering teams with win percentage less than 40%, and saves it into a JSON file.

2. Test Case 2: Retrieves Oscar-winning films data, including titles, nominations, awards, and whether it won Best Picture, then stores it in a JSON file.


TestNG Instructions:
1. I also want to tell, Follow TestNG setup folder structure into your 'build.gradle' is important to run your Unit Testacses,so please follow the Link which is under Important Links.
In breif: 
1. Add dependency of testNG (//https://mvnrepository.com/artifact/org.testng/testng/7.9.0
    testImplementation group: 'org.testng', name: 'testng', version: '6.14.3')
2. cretae a test task like this (test{})
3. Inside test  give useTestNG() {provide your testng.xml path}
4. make sure to save your build or refresh the build.
5. IMPORTANT( when u create a unit test file u will easily annotate using testNG annotations,if not ur testNG setup or xml is having issue.)

JACKSON Library : 
Simplifies handling JSON data in Java by providing tools for converting Java objects to JSON and vice versa.


> Example:
```
# to run the project
./gradlew test
```

## Important Links:
Details about checklist to set up your project in Local,follow these:
https://docs.google.com/spreadsheets/d/1Xj_PDR2gZC_T5p3yafkb1XJgRorzL034s2tsXoPyips/edit#gid=0

This is a TestNG setup to do in your Project:
https://docs.google.com/document/d/1D6Gj7eLWDoCVPFf7RZiM-i1ghRDwx-b9VcFpkAKp9Ys/edit#heading=h.fy8vja196thb
 
