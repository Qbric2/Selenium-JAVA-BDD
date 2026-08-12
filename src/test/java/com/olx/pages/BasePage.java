package com.olx.pages;

import com.olx.utils.ConfigurationManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Arrays;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        long explicitWaitSeconds = parsePositiveLong(ConfigurationManager.getProperty("explicit.wait.seconds"), 4);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWaitSeconds));
        this.wait.pollingEvery(Duration.ofMillis(200));
    }

    private long parsePositiveLong(String value, long defaultValue) {
        try {
            long parsed = Long.parseLong(value);
            return parsed > 0 ? parsed : defaultValue;
        } catch (Exception ignored) {
            return defaultValue;
        }
    }

    private WebElement resolveOrNull(By... ranked) {
        for (By by : ranked) {
            try {
                java.util.List<WebElement> h = driver.findElements(by);
                if (h.size() == 1) return h.get(0);
            } catch (org.openqa.selenium.InvalidSelectorException ignored) {}
        }
        for (By by : ranked) {
            try {
                for (WebElement e : driver.findElements(by))
                    if (e.isDisplayed()) return e;
            } catch (org.openqa.selenium.InvalidSelectorException ignored) {}
        }
        for (By by : ranked) {
            try {
                java.util.List<WebElement> h = driver.findElements(by);
                if (!h.isEmpty()) return h.get(0);
            } catch (org.openqa.selenium.InvalidSelectorException ignored) {}
        }
        return null;
    }

    private WebElement resolveVisible(By... ranked) {
        try {
            return wait.until(d -> {
                WebElement el = resolveOrNull(ranked);
                return (el != null && el.isDisplayed()) ? el : null;
            });
        } catch (org.openqa.selenium.TimeoutException te) {
            throw new org.openqa.selenium.NoSuchElementException(
                "No locator resolved to a visible element: " + Arrays.toString(ranked));
        }
    }

    private void scrollIntoView(WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    }

    private void dismissCommonPopups() {
        By[] popupClosers = new By[] {
            By.id("onetrust-accept-btn-handler"),
            By.xpath("//button[contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'accept all')]"),
            By.xpath("//button[contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'accept')]"),
            By.xpath("//button[@aria-label='Close']")
        };

        for (By closer : popupClosers) {
            try {
                for (WebElement button : driver.findElements(closer)) {
                    if (button.isDisplayed()) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
                        return;
                    }
                }
            } catch (org.openqa.selenium.WebDriverException ignored) {
                // Ignore transient popup race conditions and continue.
            }
        }
    }

    protected void clickUnique(By... ranked) {
        dismissCommonPopups();
        WebElement el = resolveVisible(ranked);
        scrollIntoView(el);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(el)).click();
        } catch (org.openqa.selenium.ElementClickInterceptedException | org.openqa.selenium.TimeoutException intercepted) {
            dismissCommonPopups();
            wait.until(ExpectedConditions.visibilityOf(el));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
    }

    protected void typeUnique(String text, By... ranked) {
        WebElement el = resolveVisible(ranked);
        wait.until(d -> el.isEnabled());
        el.sendKeys(text);
    }

    protected void hover(By... ranked) {
        WebElement el = resolveVisible(ranked);
        scrollIntoView(el);
        new Actions(driver).moveToElement(el).perform();
    }
}
