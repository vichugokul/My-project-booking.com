Feature: Booking_Page

  @BookingTest
  Scenario: Verify the booking flow
    Given User searchs for hotel for booking by specifying check-in, check-out
    When User selects hotel for booking
    And User select no. of rooms for booking "1" and click on reserve
    And User enters booking information for booking
    And User completes booking for booking
    Then User verify the confirmation No for booking