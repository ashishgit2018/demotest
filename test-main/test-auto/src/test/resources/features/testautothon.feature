@testAuto
Feature: Test UI

Background: 
Given Read Property File "movieName.properties"

  @SN11
  Scenario Outline: Launch URL based on browser
    Given Execute "<TestCase>"
	Given I use "<Browser>"
    And I launch "<URL>"
    And I search for movie "<MovieNo>" "<MovieName>"
    Examples:
      | TestCase | Browser       | URL                  |MovieNo|MovieName|
      | TC1      | chrome       | <PROPVALUE(google.url)> |Movie1|The Godfather|