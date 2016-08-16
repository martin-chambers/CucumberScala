Feature: My First Cucumber test

  As a tester,
  I would like to utilize cucumber with Mozilla and Chrome,
  So that I can create BDD style selenium-webdriver tests.

  Scenario: Bing search, using selenium on Mozilla
    Given I have navigated to Bing using Mozilla
    When I type "Selenium" in the search box using Mozilla
    And Press <Enter> using Mozilla
    Then the page should contain more than 100 occurences of the text "Selenium" using Mozilla

  Scenario: Bing search, using selenium on Chrome
    Given I have navigated to Bing using Chrome
    When I type "Selenium" in the search box using Chrome
    And Press <Enter> using Chrome
    Then the page should contain more than 100 occurences of the text "Selenium" using Chrome

