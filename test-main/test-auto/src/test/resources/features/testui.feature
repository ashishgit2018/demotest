@ui
Feature: Test UI

@s1
Scenario Outline: Launch URL based on browser
Given I use "<Browser>"
And I launch "<URL>"
Examples:
|Browser|URL|
|chrome|https://www.amazon.in/|
|firefox|https://www.flipkart.com/|