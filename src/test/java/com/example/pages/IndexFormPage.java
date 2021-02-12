package com.example.pages;
import com.example.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class IndexFormPage extends BasePage {
		public IndexFormPage(WebDriver driver) {
				super(driver);
		}
		
		@FindBy(id = "address")
		private WebElement addressField;
		
		@FindBy(id = "summary")
		private WebElement summaryField;
		
		@Step("writes summary in the summary field")
		public void writeSummary(String summary){
				try {
						Thread.sleep(2000);
				} catch (InterruptedException e) {
						e.printStackTrace();
				}
				summaryField.click();
				summaryField.sendKeys(summary);
		}
		
		@Step("writes address in the address field in form")
		public void writeAddress(String address){
				try {
						Thread.sleep(2000);
				} catch (InterruptedException e) {
						e.printStackTrace();
				}
				addressField.click();
				addressField.sendKeys(address);
		}
}