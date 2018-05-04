Feature: Click the button
  Clicking buttons is clever

  Scenario: I see a button and click it.
    Given I have launched the application
    When I click the "Click Me" button
    Then the "Click Me" is gone
