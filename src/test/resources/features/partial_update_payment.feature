#Feature: PARTIAL Update of a payment
#
#  Scenario: The payment resource to be updated does not exist
#    When the client calls PATCH /v1/payments/any-unknown-id
#    Then it receives response status code of 404
#
#  Scenario: The payment resource to be updated does exist
#    Given it exists 1 payments
#    When the client calls PATCH to the payment URI
#    Then it receives response status code of 200
