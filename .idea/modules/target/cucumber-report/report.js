$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("first.feature");
formatter.feature({
  "line": 1,
  "name": "My First cucumber test",
  "description": "\r\nAs a tester,\r\nI would like to utilize cucumber,\r\nSo that I can create BDD style selenium-webdriver tests.",
  "id": "my-first-cucumber-test",
  "keyword": "Feature"
});
formatter.scenario({
  "line": 7,
  "name": "Google search, using selenium",
  "description": "",
  "id": "my-first-cucumber-test;google-search,-using-selenium",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 8,
  "name": "I have navigated to google",
  "keyword": "Given "
});
formatter.step({
  "line": 9,
  "name": "I search for \"selenium\"",
  "keyword": "When "
});
formatter.step({
  "line": 10,
  "name": "the page title should be selenium - Google Search",
  "keyword": "Then "
});
formatter.match({
  "location": "CucumberTest.scala:18"
});
formatter.result({
  "duration": 1028946231,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "selenium",
      "offset": 14
    }
  ],
  "location": "CucumberTest.scala:22"
});
formatter.result({
  "duration": 533841439,
  "status": "passed"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
});