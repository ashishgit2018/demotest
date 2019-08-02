Feature: Wiki and IMDB Director checker

  Scenario: Search the wiki and IMDB for asserting director names
    Given Get movie details from the properties file "movieName1.properties"
    When Search the movies in Google
    Then Assert the Wiki and IMDB Results