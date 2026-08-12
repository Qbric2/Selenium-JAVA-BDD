package com.olx.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.PageLoadStrategy;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class DriverManager {

    private WebDriver driver;

    public WebDriver getDriver() {
        if (driver == null) {
            setupDriver();
        }
        return driver;
    }

    public void setupDriver() {
        String browser = ConfigurationManager.getProperty("browser");
        if (browser == null || browser.isEmpty() || "chrome".equalsIgnoreCase(browser)) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.setPageLoadStrategy(PageLoadStrategy.NONE);
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("profile.default_content_setting_values.images", 2);
            options.setExperimentalOption("prefs", prefs);
            options.addArguments(
                    "--no-sandbox",
                    "--disable-dev-shm-usage",
                    "--window-size=1920,1080",
                    "--remote-allow-origins=*",
                    "--disable-gpu",
                    "--disable-extensions",
                    "--disable-notifications",
                    "--disable-blink-features=AutomationControlled"
            );
            if (Boolean.parseBoolean(ConfigurationManager.getProperty("headless"))) {
                options.addArguments("--headless=new");
            }
            driver = new ChromeDriver(options);
            // Keep implicit wait at zero to avoid multiplying delays with layered explicit waits.
            driver.manage().timeouts().implicitlyWait(Duration.ZERO);
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
            driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(5));
        } else if ("firefox".equalsIgnoreCase(browser)) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else {
            throw new IllegalArgumentException("Browser not supported: " + browser);
        }
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
