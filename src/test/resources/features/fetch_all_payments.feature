Feature: Fetch all payment resources

  Scenario: There are no payments
    Given no payments exist
    When the client calls GET /v1/payments
    Then 0 payments are returned
    And the links attribute contains a self to /v1/payments
    And it receives response status code of 200
    And the response has JSON format


  Scenario: There are 2 payments
    Given it exists 2 payments
    When the client calls GET /v1/payments
    Then 2 payments are returned
    And the links attribute contains a self to /v1/payments
    And it receives response status code of 200
    And the response has JSON format