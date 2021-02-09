package com.example.pages;
import com.example.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
		@FindBy(id = "address")
		private WebElement addressField;
		
		@FindBy(id = "summary")
		private WebElement summaryField;
		
		public void writeSummary(String summary){
				try {
						Thread.sleep(2000);
				} catch (InterruptedException e) {
						e.printStackTrace();
				}
				summaryField.click();
				summaryField.sendKeys(summary);
		}
		
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