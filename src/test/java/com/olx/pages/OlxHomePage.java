package com.olx.pages;

import com.olx.utils.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OlxHomePage extends BasePage {

    private final By vehiclesImage = By.xpath("//div[@data-aut-id='main-categories']//span[normalize-space()='Vehicles']");
    private final By vehiclesLinkByHref = By.xpath("//a[contains(@href,'/vehicles') and .//span]");
    private final By vehiclesTextFallback = By.xpath("//*[self::span or self::p][normalize-space()='Vehicles']");

    public OlxHomePage(DriverManager driverManager) {
        super(driverManager.getDriver());
    }

    public void navigateTo(String url) {
        driver.get(url);
    }

    public void clickVehiclesImage() {
        String urlBeforeClick = driver.getCurrentUrl();
        clickUnique(vehiclesImage, vehiclesLinkByHref, vehiclesTextFallback);
        try {
            new WebDriverWait(driver, Duration.ofSeconds(1))
                    .until(d -> !d.getCurrentUrl().equals(urlBeforeClick));
        } catch (org.openqa.selenium.TimeoutException ignored) {
            // Some OLX layouts update results on-page; keep going with resilient locators.
        }
    }
}
