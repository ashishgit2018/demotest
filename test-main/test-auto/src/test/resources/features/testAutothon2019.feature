Feature: Youtube channel Automation

  @autothon2019
  Scenario: Get video name from API new1
    Given Set the base uri as "<PROPVALUE(youtubeRestURL)>"
    And Set the Request header with key "Accept-Language" and value "en-GB,en-US;q=0.9,en;q=0.8"
    When Perform GET request where uri is "video"
    Then Validate the Response code is "200"
    And Save Json Response as "youtubeVideoName"
    And I launch "https://www.youtube.com/"
    And I do youtube search for "step-inforum"
    And Write to json file at "test.json" and value "{"team": "CME Group 1",  "video": "<PROPVALUE(youtubeVideoName)>", "upcoming-videos": [<PROPVALUE(allVideoList)>]}"
    And Execute the multipart form-data post request where uri is "<PROPVALUE(youtubeRestURL)>upload" and file name is "test.json"
    When Perform GET request where uri is "result/<PROPVALUE(uploadResult)>"
    Then Validate the Response code is "200"