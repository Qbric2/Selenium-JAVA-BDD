package com.olx.pages;

import com.olx.utils.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class OlxSearchResultsPage extends BasePage {

    // Using data-aut-id for more stable locators
    private final By sortByButton = By.xpath("//div[@data-aut-id='sort-by-button']");
    private By getSortOption(String optionName) {
        return By.xpath(String.format("//div[@data-aut-id='sort-by-options']//span[text()='%s']", optionName));
    }


    public OlxSearchResultsPage(DriverManager driverManager) {
        super(driverManager.getDriver());
    }

    public void clickSortByElement(String elementName) {
        // The element name is 'Most relevant' which is part of the button itself.
        clickUnique(sortByButton);
    }

    public void hoverSortByElement(String elementName) {
        // The element name is 'Most relevant' which is part of the button itself.
        hover(sortByButton);
    }

    public void clickSortOption(String optionName) {
        clickUnique(getSortOption(optionName));
    }
}
