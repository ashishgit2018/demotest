@testAuto
Feature: Test UI

  Background:
    Given Read Property File "movieName.properties"

  @test
  Scenario Outline: Run tests on multiple browser sequentially
    Given I use "<browser>" browser
    And I launch "<URL>"
    And I search for movies
    Examples:
      | browser       | URL                     |
      | chrome        | <PROPVALUE(google.url)> |
      | firefox       | <PROPVALUE(google.url)> |
      | ie            | <PROPVALUE(google.url)> |
      | mobile_chrome | <PROPVALUE(google.url)> |


  @crossbrowser
  Scenario: Run tests on multiple browser in parallel
    Given I use "<browser>" browser
    And I launch "<URL>"
    And I search for movies