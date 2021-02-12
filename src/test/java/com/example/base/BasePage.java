package com.example.base;

import lombok.extern.java.Log;
import org.openqa.selenium.WebDriver;

@Log
public class BasePage extends TimeMachine {
		
		public BasePage(WebDriver driver) {
				super(driver);
		}
		
}
