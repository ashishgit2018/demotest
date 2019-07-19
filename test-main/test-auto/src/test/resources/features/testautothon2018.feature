@testAuto
Feature: Test UI

  Background:
    Given Read Property File "movieName.properties"

  @SN11
  Scenario Outline: Launch URL based on browser
    Given I use "<Browser>"
    And I launch "<URL>"
    And I search for movies
    Examples:
      | Browser | URL                     |
      | chrome  | <PROPVALUE(google.url)> |