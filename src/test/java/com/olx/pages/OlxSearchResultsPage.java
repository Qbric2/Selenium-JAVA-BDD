package com.olx.pages;

import com.olx.utils.DriverManager;
import com.olx.utils.ConfigurationManager;
import org.openqa.selenium.By;

public class OlxSearchResultsPage extends BasePage {

    private final By sortByButton = By.xpath("//div[@data-aut-id='sort-by-button']");
    private final By sortByButtonByText = By.xpath("//button[.//*[contains(normalize-space(),'Most relevant')] or contains(normalize-space(),'Sort')]");
    private final By sortByButtonLoose = By.xpath("//*[contains(@data-aut-id,'sort') and (self::button or self::div)]");
    private final By sortOptionsContainer = By.xpath("//div[@data-aut-id='sort-by-options']");

    private By getSortOption(String optionName) {
        return By.xpath(String.format("//div[@data-aut-id='sort-by-options']//span[normalize-space()='%s']", optionName));
    }

    private By getSortOptionFallback(String optionName) {
        return By.xpath(String.format("//*[self::span or self::li or self::button][normalize-space()='%s']", optionName));
    }

    public OlxSearchResultsPage(DriverManager driverManager) {
        super(driverManager.getDriver());
    }

    public void clickSortByElement(String elementName) {
        clickUnique(sortByButton, sortByButtonByText, sortByButtonLoose);
    }

    public void hoverSortByElement(String elementName) {
        if (Boolean.parseBoolean(ConfigurationManager.getProperty("skip.hover"))) {
            return;
        }
        if (!driver.findElements(sortOptionsContainer).isEmpty()) {
            return;
        }
        hover(sortByButton, sortByButtonByText, sortByButtonLoose);
    }

    public void clickSortOption(String optionName) {
        clickUnique(getSortOption(optionName), getSortOptionFallback(optionName));
    }
}
