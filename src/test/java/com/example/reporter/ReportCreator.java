package com.example.reporter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class ReportCreator {
		
		private String totalBuildTime;
		private List<String[]> suiteDetails;
		private Map<String, String> suiteFailureDetails;
		private List<String[]> projectDetails;
		private Set<String> skippedTestDetails;
		
		public ReportCreator(
						String totalBuildTime,
						List<String[]> suiteDetails,
						List<String[]> projectDetails,
						Map<String, String> suiteFailureDetails,
						Set<String> skippedTestDetails
		) {
				this.totalBuildTime = totalBuildTime;
				this.suiteDetails = suiteDetails;
				this.suiteFailureDetails = suiteFailureDetails;
				this.projectDetails = projectDetails;
				this.skippedTestDetails = skippedTestDetails;
		}
		
		public String generateSummary() {
				TableCreator tb = new TableCreator();
				String summaryData = tb.generateFeatureTable(suiteDetails, projectDetails);
				if (suiteFailureDetails.size() != 0) summaryData =
								summaryData + tb.generateFailureTable(suiteFailureDetails);
				if (!skippedTestDetails.isEmpty()) summaryData =
								summaryData + tb.generateSkippedTable(skippedTestDetails);
				return summaryData;
		}
		
		public String generateCompleteReport(String reportSummary)
						throws IOException {
				Scanner scanner = new Scanner(
								Paths.get("src/test/resources/build-summary.txt"),
								StandardCharsets.UTF_8.name()
				);
				String summarry = scanner.useDelimiter("\\A").next();
				scanner.close();
				// System.getProperty("hostEnv") is got from 'hostEnv' data passed from jenkins
				String testEnvironment =
								"https://" + System.getProperty("hostEnv") + ".example.com";
				String buildURL = System.getenv().containsKey("BUILD_URL")
								? System.getenv("BUILD_URL")
								: "LOCAL_BUILD";
				summarry = summarry.replace("{{BUILD_URL}}", buildURL);
				summarry = summarry.replace("{{DURATION}}", totalBuildTime);
				summarry = summarry.replace("{{SUMMARY}}", reportSummary);
				summarry = summarry.replace("{{ENVIRONMENT}}", testEnvironment);
				return summarry;
		}
		
		public String generateMailSubject(String env, String browser, String subject)
						throws IOException {
				Scanner scanner = new Scanner(
								Paths.get("src/test/resources/mail-subject.txt"),
								StandardCharsets.UTF_8.name()
				);
				String summary = scanner.useDelimiter("\\A").next();
				scanner.close();
				subject = env.toUpperCase() + " | " + browser.toUpperCase() + " " + subject;
				summary = summary.replace("{{SUBJECT}}", subject);
				return summary;
		}
		
		public void publishReport(String reportSummary, String fileDetails)
						throws FileNotFoundException, UnsupportedEncodingException {
				PrintWriter writer = new PrintWriter(fileDetails, "UTF-8");
				writer.println(reportSummary);
				writer.close();
		}
		
		public int emailSubject() {
				int totalTest = Integer.parseInt(projectDetails.get(0)[1]);
				int passedTest = Integer.parseInt(projectDetails.get(0)[2]);
				int passPercentage = ((passedTest * 100) / totalTest);
				return passPercentage;
		}
}