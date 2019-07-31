@testAuto
Feature: Test UI

  Background:
    Given Read Property File "movieName.properties"

  @SN11
  Scenario Outline: Launch URL based on browser
    And I launch "<URL>"
    And I search for movies
    Examples:
      | URL                     |
      | <PROPVALUE(google.url)> |