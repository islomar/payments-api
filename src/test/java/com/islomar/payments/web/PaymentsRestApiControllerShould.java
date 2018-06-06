package com.islomar.payments.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.islomar.payments.core.actions.CreateOnePayment;
import com.islomar.payments.core.actions.DeleteOnePayment;
import com.islomar.payments.core.actions.FetchAllPayments;
import com.islomar.payments.core.actions.FetchOnePayment;
import com.islomar.payments.core.infrastructure.PaymentDTO;
import com.islomar.payments.core.model.exceptions.PaymentNotFoundException;
import com.islomar.payments.shared.ObjectMother;
import com.islomar.payments.shared.PaymentConverter;
import com.islomar.payments.web.response.FetchOrCreateOnePaymentResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static com.islomar.payments.shared.ObjectMother.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(PaymentsRestApiController.class)
public class PaymentsRestApiControllerShould {

    private static ObjectMapper objectMapper;
    private static PaymentConverter paymentConverter;
    private ClassLoader classLoader = getClass().getClassLoader();

    @Autowired
    private MockMvc mockMvc;

    @MockBean private CreateOnePayment createOnePayment;
    @MockBean private FetchOnePayment fetchOnePayment;
    @MockBean private DeleteOnePayment deleteOnePayment;
    @MockBean private FetchAllPayments fetchAllPayments;


    @BeforeClass
    public static void setUpClass() {
        paymentConverter = new PaymentConverter();
        objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    @Test
    public void return_code_201_when_creating_one_payment_with_all_the_mandatory_attributes() throws Exception {
        PaymentDTO paymentDTO = paymentConverter.convertJsonFileToPaymentTO(NEW_PAYMENT_COMMAND_JSON_FILE);
        System.out.println(String.format(">>>>>>>>>> Test - paymentDTO: %s", paymentDTO));
        PaymentDTO createdPaymentDTO = SerializationUtils.clone(paymentDTO);
        createdPaymentDTO.setId(ANY_VALID_PAYMENT_ID);
        System.out.println(String.format(">>>>>>>>>> Test - createdPaymentDTO: %s", createdPaymentDTO));
        when(this.createOnePayment.execute(paymentDTO)).thenReturn(createdPaymentDTO);

        RequestBuilder postRequest = post(V1_PAYMENT_API_BASE_PATH)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(paymentConverter.loadJsonFile(NEW_PAYMENT_COMMAND_JSON_FILE));

        FetchOrCreateOnePaymentResponse expectedContent = new FetchOrCreateOnePaymentResponse(createdPaymentDTO);
        mockMvc.perform(postRequest)
                .andExpect(header().string("Location", LOCALHOST_URL + V1_PAYMENT_API_BASE_PATH + "/" + createdPaymentDTO.getId()))
                .andExpect(status().isCreated())
                .andExpect(content().json(paymentConverter.convertObjectToJsonString(expectedContent)));
    }

    @Ignore
    public void return_code_400_when_creating_one_payment_with_invalid_type() throws Exception {
        System.out.println(String.format(">>>>>>>>>> START"));
        PaymentDTO paymentDTO = paymentConverter.convertJsonFileToPaymentTO("json_request_body/one_payment_with_invalid_type.json");
        System.out.println(String.format(">>>>>>>>>> Test - PaymentDTO: %s", paymentDTO));
        when(this.createOnePayment.execute(paymentDTO)).thenReturn(paymentDTO);

        RequestBuilder postRequest = post(V1_PAYMENT_API_BASE_PATH)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(paymentConverter.loadJsonFile("json_request_body/one_payment_with_invalid_type.json"));

        mockMvc.perform(postRequest)
                .andExpect(status().isBadRequest());

        System.out.println(String.format(">>>>>>>>>> END"));
    }

    @Test
    public void return_code_404_when_fetching_one_payment_and_it_does_not_exist() throws Exception {
        when(this.fetchOnePayment.execute("any-non-existing-id")).thenThrow(new PaymentNotFoundException());

        mockMvc.perform(
                get(V1_PAYMENT_API_BASE_PATH + "/" + ANY_NON_EXISTING_PAYMENT_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    public void return_code_200_when_fetching_one_payment_and_it_does_exist() throws Exception {
        PaymentDTO paymentDTO = paymentConverter.convertJsonFileToPaymentTO("json_request_body/one_payment.json");
        when(this.fetchOnePayment.execute(paymentDTO.getId())).thenReturn(paymentDTO);

        RequestBuilder getRequest = get(V1_PAYMENT_API_BASE_PATH + "/" + paymentDTO.getId())
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON);
        FetchOrCreateOnePaymentResponse expectedContent = new FetchOrCreateOnePaymentResponse(paymentDTO);
        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(content().json(paymentConverter.convertObjectToJsonString(expectedContent)));
    }

    @Test
    public void be_up_and_running() throws Exception {
        mockMvc.perform(
                get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("The server is up and running!"));
    }

    @Test
    public void return_code_5xx_when_fetching_all_payments_and_unexpected_exception_happens() throws Exception {
        when(this.fetchAllPayments.execute()).thenThrow(new NullPointerException("Something unexpected just happened!"));

        mockMvc.perform(
                get(V1_PAYMENT_API_BASE_PATH))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void return_code_200_and_empty_list_of_payments_when_fetching_all_payments_and_none_exist() throws Exception {
        when(this.fetchAllPayments.execute()).thenReturn(Collections.emptyList());

        mockMvc.perform(
                get(V1_PAYMENT_API_BASE_PATH))
                .andExpect(status().isOk())
                .andExpect(content().json(paymentConverter.loadJsonFile("expected_json_responses/empty_list_of_payments.json")));
    }

}
