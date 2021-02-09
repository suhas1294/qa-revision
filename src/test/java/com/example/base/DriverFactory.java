package com.example.base;

import com.example.utils.PropertyReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class DriverFactory {
    
    public DesiredCapabilities capabilities;
    public WebDriver driver;
    private final static String configFile = "config.properties";
    
    public WebDriver getDriver(String browser) throws IOException {
        Properties config = new PropertyReader(configFile).readPropertyFile();
        switch (browser) {
            case "firefox":
                capabilities = DesiredCapabilities.firefox();
                capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, OptionsManager.getFirefoxOptions());
                break;
            default:
                capabilities = DesiredCapabilities.chrome();
                capabilities.setCapability(ChromeOptions.CAPABILITY, OptionsManager.getChromeOptions());
        }
        String driverNodeUrl = config.getProperty("driverNodeUrl");
        driver = new RemoteWebDriver(new URL(driverNodeUrl), capabilities);
        return driver;
    }
}