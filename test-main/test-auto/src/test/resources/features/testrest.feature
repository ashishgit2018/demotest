Feature: Test Rest Feature

  Scenario: Test a Rest Get
    Given Set the base uri as "<PROPVALUE(baseUrl)>"
    And Set the Request header with key "Accept-Language" and value "en-GB,en-US;q=0.9,en;q=0.8"
    And Perform GET request where uri is "ip"