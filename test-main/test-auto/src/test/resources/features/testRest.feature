@rest
Feature: Test Rest Feature

  Scenario: Test a Rest Get
    Given Set the base uri as "<PROPVALUE(baseUrl)>"
    And Set the Request header with key "Accept-Language" and value "en-GB,en-US;q=0.9,en;q=0.8"
    When Perform GET request where uri is "ip"
    Then Validate the Response code is "200"
    And Validate Json Response Key "origin" have value "<PROPVALUE(machineIpAddress)>"
    And Save Json Response as "IPRequest"
    When Perform GET request where uri is "headers"
    Then Validate the Response code is "200"
    And Validate Json Response Key "headers.Host" have value "httpbin.org"
    And Save Json Response as "IPRequestHeaders"

  Scenario: Test a Rest Get - 2
    Given Set the base uri as "https://reqres.in/"
    When Perform GET request where uri is "api/users"
    Then Validate the Response code is "200"
    And Validate Json Response Key "data[1].first_name" have value "Janet"

  Scenario: Test Rest Post
    Given Set the base uri as "https://reqres.in/"
    And Set the Request header with key "content-type" and value "application/json"
    And Set Json payload as "{"name": "testName", "job": "testLeader"}"
    When Perform POST requset where uri is "api/users"
    Then Validate the Response code is "201"
    And Validate Json Response Key "name" have value "testName"
    And Validate Json Response Key "job" have value "testLeader"
    And Validate Json Response Key "id" is Not blank
    And Save Json Response Key-Value pair for "name" as "registerdUser"
    And Set Json payload located in file "RegisterUser.json"
    When Perform POST requset where uri is "api/register"
    Then Validate the Response code is "201"
    And Validate Json Response Key "token" is Not blank
    And Save Json Response Key-Value pair for "token" as "registeredToken"

  Scenario: Test Rest put
    Given Set the base uri as "https://reqres.in/"
    And Set the Request header with key "content-type" and value "application/json"
    And Set Json payload as "{"name": "testNewName", "job": "testNewLeader"}"
    And Perform PUT request where uri is "api/users/2"
    Then Validate the Response code is "200"
    And Validate Json Response Key "name" have value "testNewName"
    And Validate Json Response Key "job" have value "testNewLeader"
    And Validate Json Response Key "updatedAt" is Not blank
    Given Set Json payload as "{"name": "<RANDOMSTRING(10)>", "job": "<RANDOMALPHNUMER(10)>"}"
    And Save Json Request Key-Value pair for "name" as "requestName"
    And Save Json Request Key-Value pair for "job" as "requestJob"
    And Perform PUT request where uri is "api/users/2"
    Then Validate the Response code is "200"
    And Validate Json Response Key "name" have value "<PROPVALUE(requestName)>"
    And Validate Json Response Key "job" have value "<PROPVALUE(requestJob)>"
    And Validate Json Response Key "updatedAt" is Not blank

  Scenario: Test Rest Delete
    Given Set the base uri as "https://reqres.in/"
    And Perform DELETE request where uri is "api/users/2"
    Then Validate the Response code is "204"
    And Validate Json Response is blank
    When Perform DELETE request where uri is "api/users/<RANDOMINTEGER(1)>"
    Then Validate the Response code is "204"
    And Validate Json Response is blank