@ui
Feature: Test UI

  @SN10
  Scenario Outline: Launch URL based on browser
    Given Read Property File "movieName.properties"

    Examples:
      | TestCase | Browser | URL                  |
      | TC1      | chrome  | https://linkedin.com |

  @SN1
  Scenario Outline: Launch URL based on browser
    Given Execute "<TestCase>"
    Given I use "<Browser>"
    And I launch "<URL>"

    Examples:
      | TestCase | Browser       | URL                  |
      | TC1      | firefox       | https://linkedin.com |
      | TC2      | chrome        | https://facebook.com |
      | TC3      | ie            | https://google.com   |
      | TC4      | mobile_chrome | https://google.com   |


  @SN2
  Scenario Outline: Launch URL based on browser and search the product
    Given Execute "<TestCase>"
    And I use "<Browser>"
    And I launch "<URL>"
    When I search "<Item>"
    And I select "<ItemName>"

    Examples:
      | TestCase | Browser       | URL                     | Item     | ItemName            |
      #| TC1      | chrome  | <PROPVALUE(amazon.url)> | iPhone 7 | <PROPVALUE(iphone)> |
      #| TC2      | firefox | <PROPVALUE(amazon.url)> | iPhone 7 | <PROPVALUE(iphone)> |
      | TC3      | mobile_chrome | <PROPVALUE(amazon.url)> | iPhone 7 | <PROPVALUE(iphone)> |