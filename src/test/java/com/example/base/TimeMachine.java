package com.example.base;

import com.example.model.Config;
import com.example.utils.YamlReader;
import lombok.extern.java.Log;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

@Log
public class TimeMachine {
		protected WebDriver driver;
		protected Config config;
		
		public TimeMachine(WebDriver driver){
				this.driver = driver;
				config = new YamlReader("test-data/config.yaml").readConfig();
		}
		
		protected void waitForLoaderToDisappear() {
				WebDriverWait wait = new WebDriverWait(driver, 15);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("<xpath_of_loader>")));
				waitFor(2000);
		}
		
		private void waitFor(int pollingDuration) {
				try {
						Thread.sleep(pollingDuration);
				} catch (InterruptedException e) {
						e.printStackTrace();
				}
		}
		
		protected void waitForElementsToBeVisible(List<WebElement> webElements) {
				WebDriverWait wait = new WebDriverWait(driver, config.getTimeoutForWait());
				wait.until(ExpectedConditions.visibilityOfAllElements(webElements));
		}
		
		protected void waitForElement(WebElement element) {
				log.info("Waiting for element : " + element);
				int failureCount = 1;
				int pollingDuration = config.getPollingDuration();
				boolean visible = false;
				for (int i = 0; i < config.getTimeoutForWait(); i++) {
						try {
								visible = element.isDisplayed();
								if (visible) {
										break;
								}
						} catch (Exception e) {
								e.printStackTrace();
						}
						waitFor(pollingDuration);
						failureCount++;
				}
				if (!visible) {
						log.severe("************ Unable to find element \"" + element + "\". Retry count: " + (failureCount - 1));
						throw new NoSuchElementException("Element: \"" + element + "\" not found");
				}
		}
		
		protected boolean waitForElement(WebElement element, int pollingDuration) {
				boolean visible = false;
				for (int i = 0; i < config.getTimeoutForWait(); i++) {
						try {
								visible = element.isDisplayed();
								break;
						} catch (Exception e) {
								log.severe("NoSuchElementException : Unable to locate element " + element);
						}
						waitFor(pollingDuration);
				}
				return visible;
		}
		
		public boolean isVisible(WebElement element) {
				log.info("Is element visible : " + element);
				int pollingDuration = config.getPollingDuration();
				boolean visible = false;
				for (int i = 0; i < config.getTimeoutForWait(); i++) {
						try {
								if (element.isDisplayed()) {
										visible = true;
										break;
								}
						} catch (Exception e) {
						}
						waitFor(pollingDuration);
				}
				return visible;
		}
		
		protected void waitForElementToDisappear(WebElement element) {
				int pollingDuration = config.getPollingDuration();
				boolean visible = true;
				for (int i = 0; i < config.getTimeoutForWait(); i++) {
						try {
								element.isDisplayed();
						} catch (Exception e) {
								visible = false;
								break;
						}
						waitFor(pollingDuration);
				}
				if (visible) {
						throw new NoSuchElementException("Element: " + element + " did not disappear");
				}
		}
		
		protected void waitForElementToAppear(WebElement element) {
				WebDriverWait wait = new WebDriverWait(driver, config.getExplicitTimeOut());
				try {
						wait.until(ExpectedConditions.visibilityOf(element));
				} catch (TimeoutException t) {
						t.printStackTrace();
				}
		}
		
		protected void explicitlyWaitForElement(WebElement webElement) {
				log.info("Explicitly Waiting for Element : " + webElement);
				Wait<WebDriver> wait = new FluentWait<>(driver)
								.withTimeout(Duration.ofSeconds(config.getExplicitTimeOut()))
								.pollingEvery(Duration.ofSeconds(3))
								.ignoring(NoSuchElementException.class);
				wait.until(ExpectedConditions.visibilityOf(webElement));
		}
		
		protected void waitForElementToBeClickable(WebElement element) {
				int pollingDuration = config.getPollingDuration();
				WebDriverWait wait = new WebDriverWait(driver, pollingDuration);
				boolean visible = false;
				for (int i = 0; i < config.getTimeoutForWait(); i++) {
						try {
								wait.until(ExpectedConditions.elementToBeClickable(element));
								visible = element.isEnabled();
								break;
						} catch (Exception e) {
						}
						waitFor(pollingDuration);
				}
				if (!visible) {
						throw new NoSuchElementException("Element: " + element + " not clickable");
				}
		}
		
		public void sleep(int ms) {
				try {
						Thread.sleep(ms);
				} catch (InterruptedException e) {
						e.printStackTrace();
				}
		}
		
		public void waitForPageLoad() {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				
				//Initially bellow given if condition will check ready state of page.
				if (js.executeScript("return document.readyState").toString().equals("complete")) {
						log.info("Page Is loaded.");
						return;
				}
				
				//This loop will rotate for 10 times to check If page Is ready after every 1 second.
				for (int i = 0; i < 10; i++) {
						try {
								Thread.sleep(1000);
						} catch (InterruptedException e) {
						}
						//To check page ready state.
						if (js.executeScript("return document.readyState").toString().equals("complete")) {
								break;
						}
				}
		}
}
