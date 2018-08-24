@ui
Feature: Test UI

  @SN1
  Scenario Outline: Launch URL based on browser
    Given Execute "<TestCase>"
    Given I use "<Browser>"
    And I launch "<URL>"

    Examples:
      | TestCase | Browser       | URL                       |
      | TC1      | firefox       | <PROPVALUE(flipkart.url)> |
      | TC2      | chrome        | <PROPVALUE(amazon.url)>   |
      | TC3      | mobile_chrome | <PROPVALUE(flipkart.url)> |

  @SN2
  Scenario Outline: Launch URL based on browser and search the product
    Given Execute "<TestCase>"
    And I use "<Browser>"
    And I launch "<URL>"
    When I search "<Item>"
    And I select "<ItemName>"

    Examples:
      | TestCase | Browser | URL                     | Item     | ItemName            |
      | TC1      | chrome  | <PROPVALUE(amazon.url)> | iPhone 7 | <PROPVALUE(iphone)> |
      | TC2      | firefox | <PROPVALUE(amazon.url)> | iPhone 7 | <PROPVALUE(iphone)> |