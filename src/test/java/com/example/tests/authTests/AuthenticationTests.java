package com.example.tests.authTests;

import com.example.base.BaseTest;
import com.example.listeners.TestListener;
import com.example.pages.IndexFormPage;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

//@Listeners(TestListener.class)
public class AuthenticationTests extends BaseTest {
		public AuthenticationTests(){
				super();
		}
		
		@Test(description = "test to check login using email functionality")
		@Severity(SeverityLevel.CRITICAL)
		public void loginThroughEmailTest(){
				// page initialisations
				IndexFormPage indexFormPage = PageFactory.initElements(getDriver(), IndexFormPage.class);
				
				// test steps
				navigateToBaseUrl();
				indexFormPage.writeSummary("this is my summary");
		}
		
		@Test(description = "test to check login using username functionality")
		@Severity(SeverityLevel.CRITICAL)
		public void loginThroughUsernameTest(){
				// page initialisations
				IndexFormPage indexFormPage = PageFactory.initElements(getDriver(), IndexFormPage.class);
				
				// test steps
				navigateToBaseUrl();
				indexFormPage.writeSummary("this is my summary");
		}
		
		@Test(description = "test to check login using mobile & otp functionality")
		@Severity(SeverityLevel.CRITICAL)
		public void loginThroughMobileOtpTest(){
				// page initialisations
				IndexFormPage indexFormPage = PageFactory.initElements(getDriver(), IndexFormPage.class);
				
				// test steps
				navigateToBaseUrl();
				indexFormPage.writeSummary("this is my summary");
		}
		
		@Test(description = "test to check logout functionality")
		@Severity(SeverityLevel.MINOR)
		public void logoutTest(){
				// page initialisations
				IndexFormPage indexFormPage = PageFactory.initElements(getDriver(), IndexFormPage.class);
				
				// test steps
				navigateToBaseUrl();
				indexFormPage.writeAddress("this is my address");
				Assert.fail("Failing this test intentionally !");
		}
}
