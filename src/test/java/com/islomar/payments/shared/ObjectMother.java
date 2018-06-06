package com.islomar.payments.shared;

import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.model.PaymentType;
import com.islomar.payments.core.model.payment_attributes.PaymentAttributes;
import com.islomar.payments.web.NewPaymentCommand;

import java.math.BigDecimal;
import java.util.Currency;

public class ObjectMother {

    public static final String LOCALHOST_URL = "http://localhost";
    public static final String V1_PAYMENT_API_BASE_PATH = "/v1/payments";
    public static final String ANY_NON_EXISTING_PAYMENT_ID = "any-non-existing-id";
    public static final String ANY_VALID_PAYMENT_ID = "4ee3a8d8-ca7b-4290-a52c-dd5b6165ec43";
    public static final String ANY_VALID_ORGASATION_ID = "743d5b63-8e6f-432e-a8fa-c5d8d2ee5fcb";
    public static final String NEW_PAYMENT_COMMAND_JSON_FILE = "json_request_body/new_payment_command.json";

    public static Payment aPayment() {
        return Payment.builder().build();
    }

    public static NewPaymentCommand aNewPaymentCommand() {
        PaymentAttributes paymentAttributes = PaymentAttributes.builder().amount(BigDecimal.valueOf(100.21)).currency(Currency.getInstance("GBP")).build();
        return NewPaymentCommand.builder()
                .type(PaymentType.PAYMENT)
                .organisationId(ANY_VALID_ORGASATION_ID)
                .attributes(paymentAttributes)
                .build();
    }
}