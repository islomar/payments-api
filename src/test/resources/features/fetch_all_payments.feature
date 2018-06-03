Feature: Fetch all payment resources

  Scenario: There are no payments
    Given no payments exist
    When the client calls GET /v1/payments
    Then no payments are returned
    And the links attribute contains a self to /v1/payments
    And the client receives response status code of 200
    And the response has JSON format