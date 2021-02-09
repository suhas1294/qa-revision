package com.example.base;

import com.example.utils.PropertyReader;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class DriverFactory {
    public WebDriver driver;
    private final static String configFile = "config.properties";
    public MutableCapabilities capabilities;
    
    public WebDriver getDriver(String browser) throws IOException {
        Properties config = new PropertyReader(configFile).readPropertyFile();
        switch (browser) {
            case "firefox":
                capabilities = OptionsManager.getFirefoxOptions();
                break;
            default:
                capabilities = OptionsManager.getChromeOptions();
        }
        String driverNodeUrl = config.getProperty("driverNodeUrl");
        driver = new RemoteWebDriver(new URL(driverNodeUrl), capabilities);
        return driver;
    }
}