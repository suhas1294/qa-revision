package com.example.tests.formTests;

import com.example.base.BaseTest;
import com.example.listeners.TestListener;
import com.example.pages.IndexFormPage;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

//@Listeners(TestListener.class)
public class FormTests extends BaseTest {
		public FormTests(){
				super();
		}
		
		@Test(description = "Fill Summary field in the form positive case")
		@Severity(SeverityLevel.NORMAL)
		public void fillSummaryField(){
				// page initialisations
				IndexFormPage indexFormPage = PageFactory.initElements(getDriver(), IndexFormPage.class);
				
				// test steps
				navigateToBaseUrl();
				indexFormPage.writeSummary("this is my summary");
		}
		
		@Test(description = "Fill address field in the form positive case")
		@Severity(SeverityLevel.MINOR)
		public void fillAddressField(){
				// page initialisations
				IndexFormPage indexFormPage = PageFactory.initElements(getDriver(), IndexFormPage.class);
				
				// test steps
				navigateToBaseUrl();
				indexFormPage.writeAddress("this is my address");
		}
}
