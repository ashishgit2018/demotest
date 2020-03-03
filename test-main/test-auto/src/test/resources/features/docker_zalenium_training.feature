Feature: This feature is to test the Docker-Zalenium feature

  @docker
  Scenario Outline: Launch URL based on browser
    Given I use "<browser>" browser
    Given I launch "<URL>"

    Examples:
      | browser | URL                  |
      | firefox | https://linkedin.com |
      | chrome  | https://facebook.com |
