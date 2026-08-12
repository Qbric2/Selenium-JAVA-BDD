package com.olx.steps;

import com.olx.pages.OlxHomePage;
import com.olx.pages.OlxSearchResultsPage;
import com.olx.support.QbricVars;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class RecordedFlowSteps {

    private final OlxHomePage olxHomePage;
    private final OlxSearchResultsPage olxSearchResultsPage;

    public RecordedFlowSteps(OlxHomePage olxHomePage, OlxSearchResultsPage olxSearchResultsPage) {
        this.olxHomePage = olxHomePage;
        this.olxSearchResultsPage = olxSearchResultsPage;
    }

    @Given("I navigate to the {string}")
    public void iNavigateToThe(String url) {
        olxHomePage.navigateTo(QbricVars.get("BASE_URL", url));
    }

    @When("I click the {string} image")
    public void iClickTheImage(String imageName) {
        // imageName parameter is 'Vehicles' which is handled by the page object method.
        olxHomePage.clickVehiclesImage();
    }

    @And("I click the {string} element")
    public void iClickTheElement(String elementName) {
        olxSearchResultsPage.clickSortByElement(elementName);
    }

    @And("I hover the {string} element")
    public void iHoverTheElement(String elementName) {
        olxSearchResultsPage.hoverSortByElement(elementName);
    }

    @And("I click the {string} item")
    public void iClickTheItem(String itemName) {
        olxSearchResultsPage.clickSortOption(itemName);
    }
}
