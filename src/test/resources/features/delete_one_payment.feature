Feature: Delete one payment resource

  Scenario: The payment resource to be deleted does not exist
    When the client calls DELETE /v1/payments/any-unknown-id
    Then it receives response status code of 404

  Scenario: The payment resource to be deleted exists
    Given it exists 1 payments
    When the client calls DELETE to the payment URI
    Then it receives response status code of 204
