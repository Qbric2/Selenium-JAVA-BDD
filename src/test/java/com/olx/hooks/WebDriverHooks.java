package com.olx.hooks;

import com.olx.utils.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class WebDriverHooks {

    private final DriverManager driverManager;

    public WebDriverHooks(DriverManager driverManager) {
        this.driverManager = driverManager;
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        driverManager.setupDriver();
    }

    @After
    public void afterScenario(Scenario scenario) {
        driverManager.quitDriver();
    }
}
