Feature: Automate xpath page of XYR tutorials
  Background: Open chrome browser
    Given Start chrome browser

    Scenario: Testing xyr tutorial page with cucumber
      Given Go to XYR tutorial dropdown page
      When select coursename and ide name
      Then close  the browser