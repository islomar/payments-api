package com.islomar.payments.shared;

import com.islomar.payments.core.model.Payment;

public class ObjectMother {

    public static final String LOCALHOST_URL = "http://localhost";
    public static final String V1_PAYMENT_API_BASE_PATH = "/v1/payments";
    public static final String ANY_NON_EXISTING_PAYMENT_ID = "any-non-existing-id";
    public static final String ANY_VALID_PAYMENT_ID = "4ee3a8d8-ca7b-4290-a52c-dd5b6165ec43";
    public static final String NEW_PAYMENT_COMMAND_JSON_FILE = "json_request_body/new_payment_command.json";

    public static Payment aPayment() {
        return Payment.builder().build();
    }
}
