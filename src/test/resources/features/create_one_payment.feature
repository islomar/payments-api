Feature: Create one payment resource

  Scenario: The payment resource contains all the required attributes
    #Given the REST API version is v1
    When the client calls POST /v1/payments
    Then it receives response status code of 201 2
    And it receives the resource URI in the Location header