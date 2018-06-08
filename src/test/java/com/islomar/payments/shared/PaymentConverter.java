package com.islomar.payments.shared;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.islomar.payments.core.infrastructure.PaymentDTO;
import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.model.exceptions.InvalidFieldError;
import com.islomar.payments.web.UpsertPaymentCommand;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class PaymentConverter {

    private static ObjectMapper objectMapper;
    private static ClassLoader classLoader;

    public PaymentConverter() {
        classLoader = getClass().getClassLoader();
        objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    public String loadJsonFile(String filename) throws IOException {
        return IOUtils.toString(readFile(filename), StandardCharsets.UTF_8);
    }

    public static PaymentDTO convertJsonFileToPaymentDTO(String filename) throws IOException {
        return objectMapper.readValue(readFile(filename), PaymentDTO.class);
    }

    public static Payment convertJsonFileToPayment(String filename) throws IOException {
        return objectMapper.readValue(readFile(filename), Payment.class);
    }

    public static InvalidFieldError convertStringToInvalidFieldError(String stringError) throws IOException {
        return objectMapper.readValue(stringError, InvalidFieldError.class);
    }

    public static UpsertPaymentCommand convertJsonFileToNewPaymentCommand(String filename) throws IOException {
        return objectMapper.readValue(readFile(filename), UpsertPaymentCommand.class);
    }

    public static String convertObjectToJsonString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    private static InputStream readFile(String filename) {
        return classLoader.getResourceAsStream(filename);
    }
}
