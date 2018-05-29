Feature: The server is up and running

Scenario: The server answers a basic "Hello world"
  When the client calls GET /
  Then the client receives response status code of 200
  And the client receives response body text "Hello world"