Feature: Test Nick Website

  @Nick
  Scenario: Check all looks good for the carousal section
    Given I launch "http://www.nick.com"
    When Get details from Nick Website
    Then Check all the links are working
    And Check tooltip is not blank
    And Validate all images are navigating