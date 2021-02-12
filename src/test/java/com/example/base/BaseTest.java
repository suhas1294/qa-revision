package com.example.base;

import com.example.model.Config;
import com.example.model.TestData;
import com.example.utils.PropertyReader;
import com.example.utils.YamlReader;
import lombok.extern.java.Log;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import java.io.IOException;
import java.util.Properties;

@Log
public class BaseTest {
		// driver object is made static since it will be shared with listener class for taking screenshot
		protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
		protected static ThreadLocal<Properties> config = new ThreadLocal<>();
		public String baseUrl;
		protected static ThreadLocal<TestData> testData = new ThreadLocal<>();
		protected static ThreadLocal<Config> configData = new ThreadLocal<>();
		
		public static WebDriver getDriver(){
				if (driver == null){
						try {
								driver.set(new DriverFactory().getDriver(config.get().getProperty("testInBrowser")));
						} catch (IOException e) {
								e.printStackTrace();
						}
				}
				return driver.get();
		}
		
		public BaseTest() {
		}
		
		@BeforeSuite
		public void dataCleanUp()  {
				log.info("Inside before suite");
		}
		
		@BeforeClass
		public void dataSetup(){
				log.info("Inside before class");
		}
		
		@BeforeTest
		public void testDataSetup() throws IOException {
				/**
				 * In parallel-tests.xml we have specified parallel as tests and thread count as 4, hence tests will
				 * be executed in parallel and in beforeTest we are assigning each thread its own data, so in this case,
				 * 4 threads will have its own data.
 				 */
				log.info(String.format("current thread name:\t %s", Thread.currentThread().getName()));
				
				testData.set(new YamlReader("test-data/test_data.yaml").readTestData());
				configData.set(new YamlReader("test-data/config.yaml").readConfig());
				config.set(new PropertyReader("config.properties").readPropertyFile());
				driver.set(new DriverFactory().getDriver(config.get().getProperty("testInBrowser")));
				this.baseUrl = config.get().getProperty("baseUrl");
				
				log.info("************** Starting test **************");
		}
		
		@AfterTest
		public void testDataCleanup(){
				log.info("************** completed test **************");
				driver.get().quit();
		}
		
		@AfterClass(alwaysRun=true)
		public void classDataCleaner(){
				log.info("inside after class");
		}
		
		@AfterSuite
		public void deleteAccount(){
				log.info("Running after suite");
		}
		
		public void navigateToBaseUrl(){
				driver.get().navigate().to(baseUrl);
		}
}
