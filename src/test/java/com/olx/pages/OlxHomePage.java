package com.olx.pages;

import com.olx.utils.DriverManager;
import org.openqa.selenium.By;

public class OlxHomePage extends BasePage {

    private final By vehiclesImage = By.xpath("//div[@data-aut-id='main-categories']//span[normalize-space()='Vehicles']");
    private final By vehiclesLinkByHref = By.xpath("//a[contains(@href,'/vehicles') and .//span]");
    private final By vehiclesTextFallback = By.xpath("//*[self::span or self::p][normalize-space()='Vehicles']");

    public OlxHomePage(DriverManager driverManager) {
        super(driverManager.getDriver());
    }

    public void navigateTo(String url) {
        driver.get(url);
        wait.until(d -> "complete".equals(
                ((org.openqa.selenium.JavascriptExecutor) d).executeScript("return document.readyState")));
    }

    public void clickVehiclesImage() {
        String urlBeforeClick = driver.getCurrentUrl();
        clickUnique(vehiclesImage, vehiclesLinkByHref, vehiclesTextFallback);
        try {
            wait.until(d -> !d.getCurrentUrl().equals(urlBeforeClick));
        } catch (org.openqa.selenium.TimeoutException ignored) {
            // Some OLX layouts update results on-page; keep going with resilient locators.
        }
    }
}
