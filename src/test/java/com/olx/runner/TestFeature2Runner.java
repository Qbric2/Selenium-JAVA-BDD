package com.olx.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features/Feature-2.feature",
    glue     = { "com.olx.steps", "com.olx.hooks" },
    plugin   = { "pretty", "html:target/cucumber-report.html" }
)
public class TestFeature2Runner { }
