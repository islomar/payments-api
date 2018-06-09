package com.islomar.payments.isolated.web;

import com.islomar.payments.core.actions.*;
import com.islomar.payments.core.infrastructure.PaymentDTO;
import com.islomar.payments.core.model.exceptions.PaymentNotFoundException;
import com.islomar.payments.shared.PaymentConverter;
import com.islomar.payments.web.PaymentsRestApiController;
import com.islomar.payments.web.response.OnePaymentResponse;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.BeforeClass;
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

import java.util.Collections;

import static com.islomar.payments.shared.ObjectMother.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(PaymentsRestApiController.class)
public class PaymentsRestApiControllerShould {

    private static PaymentConverter paymentConverter;

    @Autowired
    private MockMvc mockMvc;

    @MockBean private CreateOnePayment createOnePayment;
    @MockBean private FetchOnePayment fetchOnePayment;
    @MockBean private DeleteOnePayment deleteOnePayment;
    @MockBean private FetchAllPayments fetchAllPayments;
    @MockBean private UpdateOnePayment updateOnePayment;


    @BeforeClass
    public static void setUpClass() {
        paymentConverter = new PaymentConverter();
    }

    @Test
    public void be_up_and_running() throws Exception {
        mockMvc.perform(
                get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("The server is up and running!"));
    }

    @Test
    public void return_code_201_when_creating_one_payment_with_all_the_mandatory_attributes() throws Exception {
        PaymentDTO inputPaymentDTO = paymentConverter.convertJsonFileToPaymentDTO(NEW_PAYMENT_COMMAND_JSON_FILE);
        PaymentDTO createdPaymentDTO = SerializationUtils.clone(inputPaymentDTO);
        createdPaymentDTO.setId(ANY_VALID_PAYMENT_ID);
        when(this.createOnePayment.execute(inputPaymentDTO)).thenReturn(createdPaymentDTO);
        RequestBuilder postRequest = post(V1_PAYMENT_API_BASE_PATH)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(paymentConverter.loadJsonFile(NEW_PAYMENT_COMMAND_JSON_FILE));

        OnePaymentResponse expectedContent = new OnePaymentResponse(createdPaymentDTO);
        mockMvc.perform(postRequest)
                .andExpect(header().string("Location", LOCALHOST_URL + V1_PAYMENT_API_BASE_PATH + "/" + createdPaymentDTO.getId()))
                .andExpect(status().isCreated())
                .andExpect(content().json(paymentConverter.convertObjectToJsonString(expectedContent)));
    }

    @Test
    public void return_code_400_when_creating_one_payment_with_invalid_type() throws Exception {
        PaymentDTO paymentDtoWithInvalidType = aValidPaymentDTO();
        paymentDtoWithInvalidType.setType(null);
        when(this.createOnePayment.execute(paymentDtoWithInvalidType)).thenReturn(paymentDtoWithInvalidType);
        RequestBuilder postRequest = post(V1_PAYMENT_API_BASE_PATH)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(paymentConverter.convertObjectToJsonString(paymentDtoWithInvalidType));

        mockMvc.perform(postRequest)
                .andExpect(status().isBadRequest());
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
        PaymentDTO paymentDTO = aValidPaymentDTO();
        paymentDTO.setId(ANY_VALID_PAYMENT_ID);
        when(this.fetchOnePayment.execute(paymentDTO.getId())).thenReturn(paymentDTO);
        RequestBuilder getRequest = get(V1_PAYMENT_API_BASE_PATH + "/" + paymentDTO.getId())
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON);
        OnePaymentResponse expectedContent = new OnePaymentResponse(paymentDTO);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(content().json(paymentConverter.convertObjectToJsonString(expectedContent)));
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
