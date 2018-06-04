Feature: Fetch one payment resource

  Scenario: The payment resource requested does not exist
    When the client calls GET /v1/payments/any-unknown-id
    Then it receives response status code of 404

  #Scenario: The payment resource requested exists
    #Given an existing payment
    #When the client calls GET to the payment URI
    #Then it receives response status code of 200
    #And xxxx