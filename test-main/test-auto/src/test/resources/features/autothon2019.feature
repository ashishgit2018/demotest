Feature: To test youtube

  @youtube
  Scenario Outline: Youtube search
    Given I use "<browser>" browser
    And I launch "<URL>"
    And I search step-inforum
    Examples:
      | browser | URL                      |
      | chrome  | https://www.youtube.com/ |