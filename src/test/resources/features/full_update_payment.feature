Feature: FULL Update of a payment

  Scenario: The payment resource to be updated does not exist
    When the client calls PUT /v1/payments/any-unknown-id
    Then it receives response status code of 404

  Scenario: The payment resource to be updated does exist
    Given it exists 1 payments
    When the client calls PUT to the payment URI
    Then it receives response status code of 200
    And the resource version is 1
    And the response has JSON format
