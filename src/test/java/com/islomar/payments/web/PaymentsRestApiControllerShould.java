package com.islomar.payments.web;

import com.islomar.payments.core.actions.CreateOnePayment;
import com.islomar.payments.core.actions.DeleteOnePayment;
import com.islomar.payments.core.actions.FetchAllPayments;
import com.islomar.payments.core.actions.FetchOnePayment;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.management.RuntimeErrorException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PaymentsRestApiController.class)
public class PaymentsRestApiControllerShould {

    private static final String V1_PAYMENT_API_BASE_PATH = "/v1/payments";

    @Autowired
    private MockMvc mockMvc;

    @MockBean private CreateOnePayment createOnePayment;
    @MockBean private FetchOnePayment fetchOnePayment;
    @MockBean private DeleteOnePayment deleteOnePayment;
    @MockBean private FetchAllPayments fetchAllPayments;

    @Test
    public void be_up_and_running() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("The server is up and running!"));
    }

    @Test
    public void return_code_500_when_fetching_all_payments_and_unexpected_exception_happens() throws Exception {
        when(this.fetchAllPayments.execute()).thenThrow(new NullPointerException("Something unexpected just happened!"));

        mockMvc.perform(get(V1_PAYMENT_API_BASE_PATH))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void return_code_200_and_empty_list_of_payments_when_fetching_all_payments_and_none_exist() throws Exception {
        when(this.fetchAllPayments.execute()).thenReturn(Collections.emptyList());

        mockMvc.perform(get(V1_PAYMENT_API_BASE_PATH))
                .andExpect(status().isOk())
                .andExpect(content().json(loadJSONfile("expected_json_responses/emptyListOfPayments.json")));
    }

    private String loadJSONfile(String filename) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        return IOUtils.toString(classLoader.getResourceAsStream(filename), StandardCharsets.UTF_8);
    }
}
