Feature: Update a payment

  Scenario: The payment resource to be updated does not exist
    When the client calls PUT /v1/payments/any-unknown-id
    Then it receives response status code of 404
