Feature: Fetch one payment resource

  Scenario: The payment resource requested does not exist
    When the client calls GET /v1/payments/any-unknown-id
    Then it receives response status code of 404
