@ui
Feature: Test UI

  @SN1
  Scenario Outline: Launch URL based on browser
    Given Execute "<TestCase>"
    Given I use "<Browser>"
    And I launch "<URL>"

    Examples:
      | TestCase | Browser       | URL                       |
      | TC1      | chrome        | https://www.amazon.in/    |
      | TC2      | firefox       | https://www.flipkart.com/ |
      | TC3      | mobile_chrome | https://www.flipkart.com/ |

  @SN2
  Scenario Outline: Launch URL based on browser
    Given Execute "<TestCase>"
    And I use "<Browser>"
    And I launch "<URL>"
    When I search "<Item>"
    And I select "<ItemName>"

    Examples:
      | TestCase | Browser | URL                    | Item     | ItemName                    |
      | TC1      | chrome  | https://www.amazon.in/ | iPhone 7 | Apple iPhone 7 (Gold, 32GB) |
      | TC2      | firefox | https://www.amazon.in/ | iPhone 7 | Apple iPhone 7 (Gold, 32GB) |
