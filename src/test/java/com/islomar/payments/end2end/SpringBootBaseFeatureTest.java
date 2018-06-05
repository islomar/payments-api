package com.islomar.payments.end2end;

import com.islomar.payments.core.model.PaymentAttributes;
import com.islomar.payments.core.model.PaymentTO;
import com.islomar.payments.rest_api.response.DeleteOnePaymentResponse;
import com.islomar.payments.rest_api.response.FetchAllPaymentsResponse;
import com.islomar.payments.rest_api.response.FetchOrCreateOnePaymentResponse;
import com.islomar.payments.rest_api.response.PaymentResponse;
import cucumber.api.java.Before;


import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Currency;


@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class SpringBootBaseFeatureTest {


    private final String SERVER_URL = "http://localhost";
    private final String VERSION_1_PAYMENTS_PATH = "/v1/payments";
    private final TestRestTemplate restTemplate;

    @LocalServerPort
    protected int port;

    public SpringBootBaseFeatureTest() {
        restTemplate = new TestRestTemplate();
    }

    public ResponseEntity<FetchOrCreateOnePaymentResponse> fetchOnePayment(String paymentId) {
        return restTemplate.getForEntity(generatePaymentURI(paymentId), FetchOrCreateOnePaymentResponse.class);
    }

    public ResponseEntity<FetchAllPaymentsResponse> fetchAllPayments() {
        return restTemplate.getForEntity(generateBaseApiUri(), FetchAllPaymentsResponse.class);
    }

    public ResponseEntity<FetchOrCreateOnePaymentResponse> createOnePayment() {
        CreateOnePaymentRequest createOnePaymentRequest = this.generateCreateOnePaymentRequest();
        return restTemplate.postForEntity(generateBaseApiUri(), createOnePaymentRequest, FetchOrCreateOnePaymentResponse.class);
    }

    public ResponseEntity<DeleteOnePaymentResponse> deleteOnePayment(String paymentId) {
        RequestEntity<DeleteOnePaymentResponse> entity = new RequestEntity<>(HttpMethod.DELETE, generatePaymentURI(paymentId));
        return restTemplate.exchange(entity, DeleteOnePaymentResponse.class);
    }

    private CreateOnePaymentRequest generateCreateOnePaymentRequest() {
        PaymentAttributes paymentAttributes = new PaymentAttributes(BigDecimal.valueOf(100.21), Currency.getInstance("GBP"), null, null, null);
        return new CreateOnePaymentRequest(null, null, null, paymentAttributes);
    }


    private URI generatePaymentURI(String paymentId) {
        return URI.create(generateBaseApiUri().toString() + "/" + paymentId);
    }

    private URI generateBaseApiUri() {
        return URI.create(SERVER_URL + ":" + port + VERSION_1_PAYMENTS_PATH);
    }
}