package com.example.tests.authTests;

import com.example.base.BaseTest;
import com.example.pages.IndexFormPage;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ForgotPassword extends BaseTest {
		WebDriver driver = getDriver();
		
		public ForgotPassword(){
				super();
		}
		
		@Test(description = "test to forgot password functionality")
		@Severity(SeverityLevel.NORMAL)
		public void forgotPasswordTest(){
				// page initialisations
				IndexFormPage indexFormPage = PageFactory.initElements(getDriver(), IndexFormPage.class);
				
				// test steps
				navigateToBaseUrl();
				indexFormPage.writeAddress("Forgot my password");
				Assert.fail("Failing this test intentionally !");
		}
		
		@Test(description = "test to forgot email functionality")
		@Severity(SeverityLevel.NORMAL)
		public void forgotEmailTest(){
				// page initialisations
				IndexFormPage indexFormPage = PageFactory.initElements(getDriver(), IndexFormPage.class);
				
				// test steps
				navigateToBaseUrl();
				indexFormPage.writeAddress("Forgot my email");
				Assert.assertEquals("html", getDriver().getTitle(), "Title of page did not match");
		}
		
		@Test(description = "test to forgot username functionality")
		@Severity(SeverityLevel.NORMAL)
		public void forgotUserNameTest(){
				// page initialisations
				IndexFormPage indexFormPage = PageFactory.initElements(getDriver(), IndexFormPage.class);
				
				// test steps
				navigateToBaseUrl();
				indexFormPage.writeAddress("Forgot my username");
				Assert.assertEquals("html", getDriver().getTitle(), "Title of page did not match");
		}
}
