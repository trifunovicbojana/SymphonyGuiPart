Feature: Part I

  Background:
    Given User opens maximized browser and navigates to Homepage URL

  Scenario: Navigating to Symphony URL and maximizing the window
    Then Checking if Homepage Url is opened
    And Checking Homepage logo is displayed

  Scenario: Opening the About Us-Company section and checking information and URL
    When User navigates to the About Us "Company" section
    Then User should see the information about company
    And Correct Url for About us is opened

  Scenario: Opening Careers and checking Current Openings
    When User clicks on the "Careers" link
    Then Counting and checking the number of open positions from all locations

  Scenario: Creating txt document with open position data
    When User clicks on the "Careers" link
    Then Collecting the data and creating txt document
    And Checking if txt document is created

