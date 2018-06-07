package com.islomar.payments.shared;

import com.islomar.payments.core.infrastructure.PaymentDTO;
import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.model.payment_attributes.PaymentAttributes;
import com.islomar.payments.web.UpsertPaymentCommand;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;

public class ObjectMother {

    private static final PaymentConverter paymentConverter = new PaymentConverter();
    public static final String LOCALHOST_URL = "http://localhost";
    public static final String V1_PAYMENT_API_BASE_PATH = "/v1/payments";
    public static final String ANY_NON_EXISTING_PAYMENT_ID = "any-non-existing-id";
    public static final String ANY_VALID_PAYMENT_ID = "4ee3a8d8-ca7b-4290-a52c-dd5b6165ec43";
    public static final String ANY_VALID_ORGANISATION_ID = "743d5b63-8e6f-432e-a8fa-c5d8d2ee5fcb";
    public static final String NEW_PAYMENT_COMMAND_JSON_FILE = "json_payments/new_payment_command.json";
    public static final String VALID_PAYMENT_JSON_FILE = "json_payments/one_valid_payment.json";


    public static PaymentDTO aDummyPaymentDTO() {
        return PaymentDTO.builder().build();
    }

    public static Payment anEmptyPayment() {
        return Payment.builder().build();
    }

    public static Payment aValidPayment() throws IOException {
        return paymentConverter.convertJsonFileToPayment(VALID_PAYMENT_JSON_FILE);
    }

    public static Payment.PaymentBuilder aPaymentBuilder() {
        return Payment.builder();
    }

    public static UpsertPaymentCommand aNewPaymentCommand() throws IOException {
        return paymentConverter.convertJsonFileToNewPaymentCommand(NEW_PAYMENT_COMMAND_JSON_FILE);
    }

    public static UpsertPaymentCommand aNewPaymentCommandWithoutType() {
        PaymentAttributes paymentAttributes = generatePaymentAttributes();
        return UpsertPaymentCommand.builder()
                .organisationId(ANY_VALID_ORGANISATION_ID)
                .attributes(paymentAttributes)
                .build();
    }

    private static PaymentAttributes generatePaymentAttributes() {
        return PaymentAttributes.builder().amount(BigDecimal.valueOf(100.21)).currency(Currency.getInstance("GBP")).build();
    }
}
