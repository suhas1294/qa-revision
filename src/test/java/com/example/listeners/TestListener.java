package com.example.listeners;

import com.example.base.BaseTest;
import io.qameta.allure.Attachment;
import lombok.extern.java.Log;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import java.util.concurrent.TimeUnit;

@Log
public class TestListener implements ITestListener, ISuiteListener, IExecutionListener {
		
		private int suiteTotalTests;
		private int suitePassedTests;
		private int suiteFailedTests;
		private int suiteSkippedTests;
		private long startTime;
		
		@Attachment(value = "page_screenshot", type = "image/png")
		public byte[] saveScreenshotPNG(WebDriver driver){
				return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
		}
		
		
		@Attachment(value = "Text logs", type = "text/plain")
		public String saveTextlogs(String message){
				return message;
		}
		
		
		// Method to convert epoche time to human readable time
		private String time(long millis) {
				long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
				long seconds = TimeUnit.MILLISECONDS.toSeconds(millis - TimeUnit.MINUTES.toMillis(minutes));
				return (minutes + " minutes " + seconds + " seconds");
		}
		
		@Override
		public void onExecutionStart() {
				startTime = System.currentTimeMillis();
		}
		
		@Override
		public void onExecutionFinish() {
				String totalBuildTime = time(System.currentTimeMillis() - startTime);
				log.info(String.format("Took total of %s time to complete the tests", totalBuildTime));
		}
		
		@Override
		public void onStart(ISuite suite) {
				suiteTotalTests = 0;
				suitePassedTests = 0;
				suiteFailedTests = 0;
				suiteSkippedTests = 0;
				log.info("--------------------------------------------------------------------------------------");
				log.info("Suite Execution Started -> " + suite.getName());
				log.info("--------------------------------------------------------------------------------------");
		}
		
		@Override
		public void onFinish(ISuite suite) {
				log.info("--------------------------------------------------------------------------------------");
				log.info("Suite Execution Finished -> " + suite.getName());
				log.info("--------------------------------------------------------------------------------------");
		}
		
		@Override
		public void onTestSuccess(ITestResult result) {
				String methodName = result.getName().trim();
				log.info(String.format("%s completed successfully", methodName));
		}
		
		@Override
		public void onTestFailure(ITestResult result) {
				String methodName = result.getName().trim();
				WebDriver driver = BaseTest.getDriver();
				log.warning(String.format("Test %s failed, taking screenshot", methodName));
				saveScreenshotPNG(driver);
				saveTextlogs(result.getThrowable().toString());
				log.info(String.format("%s failed with error: %s", methodName, result.getThrowable().toString()));
		}
		
		@Override
		public void onTestSkipped(ITestResult result) {
				log.info(String.format("*** Skipping test:\t%s ***", result.getName().trim()));
		}
}