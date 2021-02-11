package com.example.listeners;

import com.example.base.BaseTest;
import com.example.reporter.ReportCreator;
import io.qameta.allure.Attachment;
import lombok.extern.java.Log;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Log
public class TestListener implements ITestListener, ISuiteListener, IExecutionListener {
		
		private int suiteTotalTests;
		private int suitePassedTests;
		private int suiteFailedTests;
		private int suiteSkippedTests;
		private long startTime;
		private static int totalTests;
		private static int totalPassedTests;
		private static int totalFailedTests;
		private static int totalSkippedTests;
		private static Map<String, String> suiteFailureDetails;
		private static Set<String> skippedTestDetails;
		private static List<String[]> suiteDetails;
		private static List<String[]> projectDetails;
		
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
				suiteDetails = new ArrayList<String[]>();
				projectDetails = new ArrayList<String[]>();
				suiteFailureDetails = new LinkedHashMap<String, String>();
				skippedTestDetails = new LinkedHashSet<String>();
		}
		
		@Override
		public void onExecutionFinish() {
				String totalBuildTime = time(System.currentTimeMillis() - startTime);
				log.info(String.format("Took total of %s time to complete the tests", totalBuildTime));
				projectDetails.add(addProjectDetails());
				ReportCreator rc = new ReportCreator(totalBuildTime, suiteDetails, projectDetails, suiteFailureDetails, skippedTestDetails);
				String reportSummary = rc.generateSummary();
				String passPercent = Integer.toString(rc.emailSubject());
				String browser = System.getProperty("browser"); // System.getProperty is got by passing 'browser' data from jenkins
				String env = System.getProperty("hostEnv"); // // System.getProperty is got by passing 'hostEnv' data from jenkins
				
				try {
						String completeReport = rc.generateCompleteReport(reportSummary);
						rc.publishReport(completeReport, "target/summary.html");
						String emailSubject = rc.generateMailSubject(env, browser, passPercent);
						rc.publishReport(emailSubject, "target/mailSubject.txt");
				} catch (IOException e) {
						e.printStackTrace();
				}
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
				if (suiteTotalTests > 0)
						suiteDetails.add(addSuiteDetails(suite));
		}
		
		@Override
		public void onTestSuccess(ITestResult result) {
				String methodName = result.getName().trim();
				log.info(String.format("%s completed successfully", methodName));
				// report mailer
				addToTotalTestsInSuite();
				addToSuccessTestsInSuite();
		}
		
		@Override
		public void onTestFailure(ITestResult result) {
				String methodName = result.getName().trim();
				WebDriver driver = BaseTest.getDriver();
				log.warning(String.format("Test %s failed, taking screenshot", methodName));
				saveScreenshotPNG(driver);
				saveTextlogs(result.getThrowable().toString());
				log.info(String.format("%s failed with error: %s", methodName, result.getThrowable().toString()));
				// report mailer
				addToTotalTestsInSuite();
				addToFailedTestsInSuite();
		}
		
		@Override
		public void onTestSkipped(ITestResult result) {
				log.info(String.format("*** Skipping test:\t%s ***", result.getName().trim()));
				// report mailer
				addToTotalTestsInSuite();
				addToSkippedTestsInSuite();
				skippedTestDetails.add(result.getMethod().getTestClass().getName() + "|" + result.getName());
		}
		
		// --------------------- code required for report mailer start ---------------------
		
		private void addToTotalTestsInSuite() {
				suiteTotalTests = suiteTotalTests + 1;
		}
		
		private void addToFailedTestsInSuite() {
				suiteFailedTests = suiteFailedTests + 1;
		}
		
		private void addToSuccessTestsInSuite() {
				suitePassedTests = suitePassedTests + 1;
		}
		
		private void addToSkippedTestsInSuite() {
				suiteSkippedTests = suiteSkippedTests + 1;
		}
		
		private String[] addSuiteDetails(ISuite arg0) {
				String[] suiteDetailsArray = new String[5];
				suiteDetailsArray[0] = arg0.getName();
				suiteDetailsArray[1] = Integer.toString(suiteTotalTests);
				suiteDetailsArray[2] = Integer.toString(suitePassedTests);
				suiteDetailsArray[3] = Integer.toString(suiteFailedTests);
				suiteDetailsArray[4] = Integer.toString(suiteSkippedTests);
				
				totalTests = totalTests + suiteTotalTests;
				totalPassedTests = totalPassedTests + suitePassedTests;
				totalFailedTests = totalFailedTests + suiteFailedTests;
				totalSkippedTests = totalSkippedTests + suiteSkippedTests;
				return suiteDetailsArray;
		}
		
		private String[] addProjectDetails() {
				String[] projectDetails = new String[5];
				projectDetails[0] = "Total";
				projectDetails[1] = Integer.toString(totalTests);
				projectDetails[2] = Integer.toString(totalPassedTests);
				projectDetails[3] = Integer.toString(totalFailedTests);
				projectDetails[4] = Integer.toString(totalSkippedTests);
				return projectDetails;
		}
		
		// --------------------- code required for report mailer end ---------------------
}