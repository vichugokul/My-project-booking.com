Feature: Hotel_Details_Page

  @HomeDetailsTest
  Scenario: Verify hotel Details - hotel name, Hotel Desc, Room Type
    Given User searchs for hotel, check-in, check-out
    Then Verify the hotel name
    And Verify the hotel desc in search results
    And Verify the hotel amenities and room Type

  @HomeDetailsTest
  Scenario: Verify hotel booking page navigation
    Given User searchs for hotel, check-in, check-out
    Then Verify the hotel name
    When User select no. of rooms "1" and click on reserve
    Then User verifies the booking navigation
