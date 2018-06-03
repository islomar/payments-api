package com.islomar.payments.end2end;

import com.islomar.payments.api.FetchAllPaymentsResponse;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


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

    public ResponseEntity<FetchAllPaymentsResponse> fetchOnePayment(String paymentId) {
        return restTemplate.getForEntity(SERVER_URL + ":" + port + VERSION_1_PAYMENTS_PATH + "/" + paymentId, FetchAllPaymentsResponse.class);
    }

    public ResponseEntity<FetchAllPaymentsResponse> fetchAllPayments() {
        return restTemplate.getForEntity(SERVER_URL + ":" + port + VERSION_1_PAYMENTS_PATH, FetchAllPaymentsResponse.class);
    }
}