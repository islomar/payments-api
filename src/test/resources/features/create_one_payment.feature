Feature: Create one payment resource

  Scenario: The payment resource contains all the required attributes
    When the client calls POST /v1/payments
    Then it receives response status code of 201
    And it receives the resource URI in the Location header
    And the self link attribute points to the payment URI

#  Scenario: The payment resource does not contain all the required attributes
#    When the client calls POST /v1/payments
#    Then it receives response status code of 400
