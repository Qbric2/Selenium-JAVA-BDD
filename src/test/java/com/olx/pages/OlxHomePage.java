package com.olx.pages;

import com.olx.utils.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OlxHomePage extends BasePage {

    private final By vehiclesImage = By.xpath("//div[@data-aut-id='main-categories']//span[text()='Vehicles']");
    // The locator from the plan was too generic. Using a more specific one.

    public OlxHomePage(DriverManager driverManager) {
        super(driverManager.getDriver());
    }

    public void navigateTo(String url) {
        driver.get(url);
    }

    public void clickVehiclesImage() {
        clickUnique(vehiclesImage);
    }
}
