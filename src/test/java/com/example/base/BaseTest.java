package com.example.base;

import com.example.utils.PropertyReader;
import lombok.extern.java.Log;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import java.io.IOException;
import java.util.Properties;

@Log
public class BaseTest {
		// driver object is made static since it will be shared with listener class for taking screenshot
		protected static WebDriver driver;
		protected static Properties config;
		public String baseUrl;
		
		public static WebDriver getDriver(){
				if (driver == null){
						try {
								return new DriverFactory().getDriver(config.getProperty("testInBrowser"));
						} catch (IOException e) {
								e.printStackTrace();
						}
				}
				return driver;
		}
		
		public BaseTest() {
				try {
						config = new PropertyReader("config.properties").readPropertyFile();
				} catch (IOException e) {
						e.printStackTrace();
				}
		}
		
		@BeforeSuite
		public void dataCleanUp()  {
				log.info("Inside before suite");
		}
		
		@BeforeClass
		public void dataSetup(){
				try {
						driver = new DriverFactory().getDriver(config.getProperty("testInBrowser"));
						this.baseUrl = config.getProperty("baseUrl");
				} catch (IOException e) {
						log.severe("Unable to initialise the driver");
				}
				log.info("Inside before class");
		}
		
		@BeforeTest
		public void testDataSetup(){
				log.info("************** Starting test **************");
		}
		
		@AfterTest
		public void testDataCleanup(){
				log.info("************** completed test **************");
		}
		
		@AfterClass(alwaysRun=true)
		public void classDataCleaner(){
				log.info("inside after class");
				driver.quit();
		}
		
		@AfterSuite
		public void deleteAccount(){
				log.info("Running after suite");
		}
		
		public void navigateToBaseUrl(){
				driver.navigate().to(baseUrl);
		}
}
