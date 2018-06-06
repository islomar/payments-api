Feature: Fetch one payment resource

  Scenario: The payment resource requested does not exist
    When the client calls GET /v1/payments/any-unknown-id
    Then it receives response status code of 404

  Scenario: The payment resource requested exists
    Given it exists 1 payments
    When the client calls GET to the payment URI
    Then it receives response status code of 200
    And the self link attribute points to the payment URI
    And the response has JSON format