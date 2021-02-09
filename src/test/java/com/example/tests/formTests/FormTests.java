package com.example.tests.formTests;

import com.example.base.BaseTest;
import com.example.pages.IndexFormPage;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

public class FormTests extends BaseTest {
		public FormTests(){
				super();
		}
		
		@Test(description = "Fill Summary field in the form positive case")
		public void fillSummaryField(){
				// page initialisations
				IndexFormPage indexFormPage = PageFactory.initElements(driver, IndexFormPage.class);
				
				// test steps
				navigateToBaseUrl();
				indexFormPage.writeSummary("this is my summary");
		}
		
		@Test(description = "Fill address field in the form positive case")
		public void fillAddressField(){
				// page initialisations
				IndexFormPage indexFormPage = PageFactory.initElements(driver, IndexFormPage.class);
				
				// test steps
				navigateToBaseUrl();
				indexFormPage.writeAddress("this is my address");
		}
}
