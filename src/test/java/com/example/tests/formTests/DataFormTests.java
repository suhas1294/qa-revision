package com.example.tests.formTests;

import com.example.base.BaseTest;
import com.example.pages.IndexFormPage;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class DataFormTests extends BaseTest {
		public DataFormTests(){
				super();
		}
		
		@DataProvider(name="scientists")
		public Object[][] getScientistData(){
				return new Object[][]
						{
										{ "Aryabhatta", "mathematician-astronomers" },
										{ "Sushruta", "physician" },
										{ "Patanjali", "Yogi" }
						};
		}
		
		@Test(description = "Fill the summary field by fetching data from yaml")
		@Severity(SeverityLevel.NORMAL)
		public void staticDataTest(){
				// page initialisations
				IndexFormPage indexFormPage = PageFactory.initElements(driver, IndexFormPage.class);
				
				// test steps
				navigateToBaseUrl();
				indexFormPage.writeSummary(testData.getSummary());
		}
		
		@Test(description = "example test which utilises testng data provider", dataProvider = "scientists")
		public void dataProviderExample(String personName, String field){
				IndexFormPage indexFormPage = PageFactory.initElements(driver, IndexFormPage.class);
				
				// test steps
				navigateToBaseUrl();
				indexFormPage.writeSummary(String.format("%s was a great %s", personName, field));
		}
		
		@Parameters({"isPremiumAccount", "isAccountSuspended"})
		@Test(description = "example usage of parameterised data provider from xml")
		public void parameterisedAnnotation(@Optional("true") String isPremiumAccount,@Optional("does not matter") String isAccountSuspended ){
				IndexFormPage indexFormPage = PageFactory.initElements(driver, IndexFormPage.class);
				
				// test steps
				navigateToBaseUrl();
				indexFormPage.writeSummary(String.format("Data is read from parameters fetched from xml files %s and %s", isPremiumAccount, isAccountSuspended));
				System.out.println(String.format("Data is read from parameters fetched from xml files %s and %s", isPremiumAccount, isAccountSuspended));
		}
}
