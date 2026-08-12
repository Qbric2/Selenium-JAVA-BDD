Feature: Recorded Flow

  Scenario: User navigates and sorts vehicles
    Given I navigate to the "https://www.olx.com.pk/"
    When I click the "Vehicles" image
    And I click the "Most relevant" element
    And I hover the "Most relevant" element
    And I click the "Highest price" item
