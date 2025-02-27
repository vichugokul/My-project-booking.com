Feature: Home_Page

@HomePageTest
Scenario: Verify display of promo banner and offers
  Given User searchs for dest, check-in, check-out
  Then Verify search Results

@HomePageTest
  Scenario: Verify search result after filtering price
    Given User searchs for dest, check-in, check-out
    When User sorts the result based on price
    Then User verify the sorted list

  @HomePageTest
  Scenario: Verify search result after filtering based on rating
    Given User searchs for dest, check-in, check-out
    When User sorts the result based on rate "2"
    Then User verify the filtered result based on rating "2"

  @HomePageTest
  Scenario: Verify search result after filtering based on distance from downcenter
    Given User searchs for dest, check-in, check-out
    When User sorts the result based on distance from downtown "1"
    Then User verify the filtered result based on downtown distance "1"










