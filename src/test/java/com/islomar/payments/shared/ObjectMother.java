package com.islomar.payments.shared;

import com.islomar.payments.core.infrastructure.InMemoryPaymentRepository;
import com.islomar.payments.core.infrastructure.PaymentDTO;
import com.islomar.payments.core.infrastructure.PaymentMapper;
import com.islomar.payments.core.model.*;
import com.islomar.payments.web.UpsertPaymentCommand;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;

public class ObjectMother {

    private static final String VALID_PAYMENT_JSON_FILE = "json_payments/one_valid_payment.json";
    public static final String LOCALHOST_URL = "http://localhost";
    public static final String V1_PAYMENT_API_BASE_PATH = "/v1/payments";
    public static final String ANY_NON_EXISTING_PAYMENT_ID = "any-non-existing-id";
    public static final String ANY_VALID_PAYMENT_ID = "4ee3a8d8-ca7b-4290-a52c-dd5b6165ec43";
    public static final String NEW_PAYMENT_COMMAND_JSON_FILE = "json_payments/new_payment_command.json";
    private static PaymentDTO validPaymentDTO;
    private static Payment validPayment;
    private static UpsertPaymentCommand upsertPaymentCommand;
    private static PaymentConverter paymentConverter = new PaymentConverter();


    public static PaymentDTO anEmptyPaymentDTO() {
        return PaymentDTO.builder().build();
    }

    public static Payment anEmptyPayment() {
        return Payment.builder().build();
    }

    public static Payment aValidPayment() throws IOException {
        if (validPayment == null) {
            validPayment = paymentConverter.convertJsonFileToPayment(VALID_PAYMENT_JSON_FILE);
        }
        return SerializationUtils.clone(validPayment);
    }

    public static PaymentDTO aValidPaymentDTO() throws IOException {
        if (validPaymentDTO == null) {
            validPaymentDTO = paymentConverter.convertJsonFileToPaymentDTO(NEW_PAYMENT_COMMAND_JSON_FILE);
        }
        return SerializationUtils.clone(validPaymentDTO);
    }

    public static Payment.PaymentBuilder aPaymentBuilder() {
        return Payment.builder();
    }

    public static UpsertPaymentCommand anUpsertPaymentCommand() throws IOException {
        if (upsertPaymentCommand == null) {
            upsertPaymentCommand = paymentConverter.convertJsonFileToNewPaymentCommand(NEW_PAYMENT_COMMAND_JSON_FILE);
        }
        return SerializationUtils.clone(upsertPaymentCommand);
    }

    public static UpsertPaymentCommand anUpsertPaymentCommandWithoutType() throws IOException {
        UpsertPaymentCommand upsertPaymentCommand = anUpsertPaymentCommand();
        upsertPaymentCommand.setType(null);
        return upsertPaymentCommand;
    }

    public static PaymentService aPaymentService(PaymentRepository paymentRepository) {
        return new PaymentService(paymentRepository, aPaymentValidator());
    }

    public static InMemoryPaymentRepository anInMemoryPaymentRepository() {
        return new InMemoryPaymentRepository();
    }

    public static PaymentValidator aPaymentValidator() {
        return new PaymentValidator();
    }

    public static PaymentExternalValidator aPaymentExternalValidator() {
        return new PaymentExternalValidator();
    }

    public static PaymentMapper aPaymentMapper() {
        return new PaymentMapper();
    }
}
