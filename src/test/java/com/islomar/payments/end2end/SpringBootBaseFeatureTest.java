package com.islomar.payments.end2end;

import com.islomar.payments.web.UpsertPaymentCommand;
import com.islomar.payments.web.response.FetchAllPaymentsResponse;
import com.islomar.payments.web.response.OnePaymentResponse;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URI;

import static com.islomar.payments.shared.ObjectMother.*;


@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class SpringBootBaseFeatureTest {

    private final TestRestTemplate restTemplate;

    @LocalServerPort
    protected int port;

    public SpringBootBaseFeatureTest() {
        restTemplate = new TestRestTemplate();
    }

    public ResponseEntity<OnePaymentResponse> fetchOnePayment(String paymentId) {
        return restTemplate.getForEntity(generatePaymentURI(paymentId), OnePaymentResponse.class);
    }

    public ResponseEntity<FetchAllPaymentsResponse> fetchAllPayments() {
        return restTemplate.getForEntity(generateBaseApiUri(), FetchAllPaymentsResponse.class);
    }

    public ResponseEntity<OnePaymentResponse> createOnePayment(UpsertPaymentCommand upsertPaymentCommand) {
        return restTemplate.postForEntity(generateBaseApiUri(), upsertPaymentCommand, OnePaymentResponse.class);
    }

    public ResponseEntity<Object> createOnePaymentWithError(UpsertPaymentCommand upsertPaymentCommand) {
        return restTemplate.postForEntity(generateBaseApiUri(), upsertPaymentCommand, Object.class);
    }

    public ResponseEntity<OnePaymentResponse> deleteOnePayment(String paymentId) {
        RequestEntity<OnePaymentResponse> entity = new RequestEntity<>(HttpMethod.DELETE, generatePaymentURI(paymentId));
        return restTemplate.exchange(entity, OnePaymentResponse.class);
    }

    public ResponseEntity<OnePaymentResponse> updateOnePayment(String paymentId) throws IOException {
        UpsertPaymentCommand updatePaymentCommand = anUpsertPaymentCommand();
        updatePaymentCommand.setOrganisationId("updated-organisation-id");
        RequestEntity<UpsertPaymentCommand> entity = new RequestEntity<>(updatePaymentCommand, HttpMethod.PUT, generatePaymentURI(paymentId));
        return restTemplate.exchange(entity, OnePaymentResponse.class);
    }

    private URI generatePaymentURI(String paymentId) {
        return URI.create(generateBaseApiUri().toString() + "/" + paymentId);
    }

    private URI generateBaseApiUri() {
        return URI.create(LOCALHOST_URL + ":" + port + V1_PAYMENT_API_BASE_PATH);
    }
}