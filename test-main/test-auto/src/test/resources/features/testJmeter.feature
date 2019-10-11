@jmeter
Feature: Test JMeter

  Scenario: Test a GET Request
    Given Set JMeter No of threads as "10", No of loops as "10" and Ramp up as "1"
    And Set JMeter test domain as "https://amazon.com", port as "443" and Http method as "GET"
    When Execute the JMeter test
    Given Set JMeter No of threads as "1", No of loops as "1" and Ramp up as "1"
    And Set JMeter test domain as "http://google.com", port as "80" and Http method as "GET"
    When Execute the JMeter test

  Scenario: Run Jmeter tests using JMX file
    Given Load the JMeter JMX file with name as "test.jmx"
    And Execute the JMeter test