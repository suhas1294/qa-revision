package com.example.listeners;

import lombok.extern.java.Log;
import org.testng.*;
import java.util.concurrent.TimeUnit;

@Log
public class TestListener implements ITestListener, ISuiteListener, IExecutionListener {
		
		private int suiteTotalTests;
		private int suitePassedTests;
		private int suiteFailedTests;
		private int suiteSkippedTests;
		private long startTime;
		
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
				log.info(String.format("%s failed with error: %s", methodName, result.getThrowable().toString()));
		}
		
		@Override
		public void onTestSkipped(ITestResult result) {
				log.info(String.format("*** Skipping test:\t%s ***", result.getName().trim()));
		}
}