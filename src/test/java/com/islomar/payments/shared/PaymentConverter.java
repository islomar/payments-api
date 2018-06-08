package com.islomar.payments.shared;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.islomar.payments.core.infrastructure.PaymentDTO;
import com.islomar.payments.core.model.Payment;
import com.islomar.payments.web.UpsertPaymentCommand;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class PaymentConverter {

    private ObjectMapper objectMapper;
    private ClassLoader classLoader = getClass().getClassLoader();

    public PaymentConverter() {
        objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    public String loadJsonFile(String filename) throws IOException {
        return IOUtils.toString(readFile(filename), StandardCharsets.UTF_8);
    }

    public PaymentDTO convertJsonFileToPaymentDTO(String filename) throws IOException {
        return objectMapper.readValue(readFile(filename), PaymentDTO.class);
    }

    public Payment convertJsonFileToPayment(String filename) throws IOException {
        return objectMapper.readValue(readFile(filename), Payment.class);
    }

    public UpsertPaymentCommand convertJsonFileToNewPaymentCommand(String filename) throws IOException {
        return objectMapper.readValue(readFile(filename), UpsertPaymentCommand.class);
    }

    public String convertObjectToJsonString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    private InputStream readFile(String filename) {
        return this.classLoader.getResourceAsStream(filename);
    }
}
