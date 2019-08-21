Feature: Youtube channel Automation

  Scenario: Get video name from API new1
    Given Set the base uri as "<PROPVALUE(youtubeRestURL)>"
    And Set the Request header with key "Accept-Language" and value "en-GB,en-US;q=0.9,en;q=0.8"
    When Perform GET request where uri is "video"
    Then Validate the Response code is "200"
    And Save Json Response as "youtubeVideoName"
    And Write to temp properties as key "upcomingVideoList" and value ""Am I a Social Monster?","Future of Test Organizations and Testers""
    And Write to json file at "test.json" and value "{"team": "CME Group 1",  "video": "<PROPVALUE(youtubeVideoName)>", "upcoming-videos": [<PROPVALUE(upcomingVideoList)>]}"
    And Execute the multipart form-data post request where uri is "<PROPVALUE(youtubeRestURL)>upload" and file name is "test.json"
    When Perform GET request where uri is "result/<PROPVALUE(uploadResult)>"
    Then Validate the Response code is "200"