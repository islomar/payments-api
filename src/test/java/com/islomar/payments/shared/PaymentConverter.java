package com.islomar.payments.shared;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.islomar.payments.core.infrastructure.PaymentDTO;
import com.islomar.payments.web.UpsertPaymentCommand;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PaymentConverter {

    private static ObjectMapper objectMapper;
    private ClassLoader classLoader;

    public PaymentConverter() {
        this.classLoader = getClass().getClassLoader();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    public String loadJsonFile(String filename) throws IOException {
        return IOUtils.toString(this.classLoader.getResourceAsStream(filename), StandardCharsets.UTF_8);
    }

    public PaymentDTO convertJsonFileToPaymentTO(String filename) throws IOException {
        return this.objectMapper.readValue(this.classLoader.getResourceAsStream(filename), PaymentDTO.class);
    }

    public UpsertPaymentCommand convertJsonFileToNewPaymentCommand(String filename) throws IOException {
        return this.objectMapper.readValue(this.classLoader.getResourceAsStream(filename), UpsertPaymentCommand.class);
    }

    public String convertObjectToJsonString(Object object) throws JsonProcessingException {
        return this.objectMapper.writeValueAsString(object);
    }
}
