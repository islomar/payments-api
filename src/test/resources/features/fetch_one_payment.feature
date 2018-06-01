Feature: Fetch one payment resource

  Scenario: The payment resource requested does not exist
    When the client calls GET /payments/any-unknown-id
    Then the client receives response status code of 404
