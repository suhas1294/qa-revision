package com.example.base;

import org.openqa.selenium.WebElement;

public interface PageUtils {
		void hardRefreshPage();
		void clickOnElement(WebElement element);
		void jsExecuterClickOn(WebElement element);
		String getTitleOfPage();
		WebElement findElement(String method, String value);
		void scrollPageToElement(WebElement element);
		void scrollToElement(WebElement element);
		void clickOnStaleElementWithRetry(WebElement element);
		void writeTextInField(WebElement webElement, String text);
		void writeTextInRTE_Field(WebElement webElement, String text);
		void scrollToAndClickOnElement(WebElement element);
		void doubleClickOn(WebElement element);
		void hitKeyboradKey(WebElement webElement, String keyname);
		void scrollToPageTop();
		
}
