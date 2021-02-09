package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.LoginPage;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
		public LoginTest(){
				super();
		}
		@Test
		public void loginTest(){
				// page initialisations
				LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
				
				// test steps
				navigateToBaseUrl();
				loginPage.writeSummary("this is my summary");
				loginPage.writeAddress("this is my address");
		}
}
