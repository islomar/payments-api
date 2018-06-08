package com.islomar.payments.end2end.steps;

import com.islomar.payments.core.infrastructure.PaymentDTO;
import com.islomar.payments.end2end.SpringBootBaseFeatureTest;
import com.islomar.payments.shared.PaymentConverter;
import com.islomar.payments.web.response.FetchAllPaymentsResponse;
import com.islomar.payments.web.response.OnePaymentResponse;
import com.islomar.payments.web.response.PaymentResponse;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.islomar.payments.shared.ObjectMother.anUpsertPaymentCommand;
import static com.islomar.payments.shared.ObjectMother.anUpsertPaymentCommandWithoutType;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

public class PaymentsApiStepDefinitions extends SpringBootBaseFeatureTest {

    private static final String LOCALHOST = "http://localhost";
    private static final String V1_PAYMENTS_API_PATH = "/v1/payments";
    private static final String SELF_ATTRIBUTE_KEY = "self";
    private ResponseEntity<? extends PaymentResponse> paymentResponse;
    private ResponseEntity<Object> errorResponse;


    @Before
    public void setUp(){
        deleteAllPayments();
        new PaymentConverter();
    }

    private void deleteAllPayments() {
        ResponseEntity<FetchAllPaymentsResponse> allPaymentsResponse = fetchAllPayments();
        List<PaymentDTO> allPaymentDTOS = allPaymentsResponse.getBody().getData();
        for (PaymentDTO paymentDTO : allPaymentDTOS) {
            deleteOnePayment(paymentDTO.getId());
        }
    }

    @Given("^no payments exist$")
    public void noPaymentsExist() {
        // Nothing to do here
    }

    @Given("^it exists (\\d+) payments$")
    public void it_exists_payments(int numberOfPayments) throws IOException {
        for (int i=0; i< numberOfPayments; i++) {
            this.paymentResponse = createOnePayment(anUpsertPaymentCommand());
        }
    }

    @When("^the client calls GET /v1/payments/(\\S+)$")
    public void the_client_calls_GET_one_payment(String paymentId) {
        this.paymentResponse = fetchOnePayment(paymentId);
    }

    @When("^the client calls GET to the payment URI$")
    public void the_client_calls_GET_to_the_payment_URI() {
        PaymentDTO existingPayment = ((OnePaymentResponse)this.paymentResponse.getBody()).getData();
        this.paymentResponse = fetchOnePayment(existingPayment.getId());
    }

    @When("^the client calls GET /v1/payments$")
    public void the_client_calls_GET_all_payments() {
        this.paymentResponse = fetchAllPayments();
    }

    @When("^the client calls POST /v1/payments$")
    public void the_client_calls_POST() throws IOException {
        this.paymentResponse = createOnePayment(anUpsertPaymentCommand());
    }

    @When("^the client calls POST /v1/payments without type$")
    public void the_client_calls_POST_without_type() throws IOException {
        this.errorResponse = createOnePaymentWithError(anUpsertPaymentCommandWithoutType());
    }

    @When("^the client calls DELETE to the payment URI$")
    public void the_client_calls_DELETE_to_the_existing_payment_URI() {
        PaymentDTO existingPayment = (PaymentDTO)this.paymentResponse.getBody().getData();
        this.paymentResponse = deleteOnePayment(existingPayment.getId());
    }

    @When("^the client calls DELETE /v1/payments/(\\S+)$")
    public void the_client_calls_DELETE_to_payment_id(String paymentId) {
        this.paymentResponse = deleteOnePayment(paymentId);
    }

    @When("^the client calls PUT /v1/payments/(\\S+)$")
    public void the_client_calls_PUT_to_payment_id(String paymentId) throws IOException {
        this.paymentResponse = updateOnePayment(paymentId);
    }

    @When("^the client calls PUT to the payment URI$")
    public void the_client_calls_PUT_to_an_existing_payment_URI() throws IOException {
        PaymentDTO existingPayment = (PaymentDTO)this.paymentResponse.getBody().getData();
        this.paymentResponse = updateOnePayment(existingPayment.getId());
    }

    @Then("^(\\d+) payments are returned$")
    public void payments_are_returned(int expectedNumberOfPaymentsReturned) {
        List<PaymentDTO> data = (List<PaymentDTO>) paymentResponse.getBody().getData();
        assertThat(data, hasSize(expectedNumberOfPaymentsReturned));
    }

    @And("^it receives response status code of (\\d+)$")
    public void the_client_receives_response_status_code_of(int httpStatusCodeValue) {
        assertThat(this.paymentResponse.getStatusCodeValue(), is(httpStatusCodeValue));
    }

    @And("^it receives error response status code of (\\d+)$")
    public void the_client_receives_error_response_status_code_of(int httpStatusCodeValue) {
        assertThat(this.errorResponse.getStatusCodeValue(), is(httpStatusCodeValue));
    }

    @And("^it receives response body text \"([^\"]*)\"$")
    public void the_client_receives_response_body_text(String text) {
        assertThat(this.paymentResponse.getBody(), is(text));
    }

    @And("^the error response contains error message with ([^\"]*)=\"([^\"]*)\" and ([^\"]*)=\"([^\"]*)\"$")
    public void the_error_response_contains_error_message_with_fieldName(String firstKey, String firstValue, String secondKey, String secondValue) {
        List<HashMap<String, Object>> invalidFieldErrors = (List<HashMap<String, Object>>)this.errorResponse.getBody();

        assertThat(invalidFieldErrors, hasSize(1));
        assertThat(invalidFieldErrors, contains(hasEntry(firstKey, firstValue)));
        assertThat(invalidFieldErrors, contains(hasEntry(secondKey, secondValue)));
    }

    @And("^the response has JSON format$")
    public void the_response_has_JSON_format() {
        assertThat(this.paymentResponse.getHeaders().getContentType().getType(), is(APPLICATION_JSON.getType()));
    }

    @And("^the links attribute contains a self to ([^\"]*)$")
    public void the_links_attribute_contains_a_self_to_all_payments_uri(String path) {
        Map<String, URI> links = paymentResponse.getBody().getLinks();
        assertNotNull("Missing 'links' attribute", links);
        assertThat("Missing 'self' attribute", links, hasKey(SELF_ATTRIBUTE_KEY));

        URI expectedUri= URI.create(LOCALHOST + ":" + this.port + path);
        assertThat(links, hasEntry(SELF_ATTRIBUTE_KEY, expectedUri));
    }

    @And("^the self link attribute points to the payment URI$")
    public void theSelfLinkAttributePointsToThePaymentURI() {
        Map<String, URI> links = paymentResponse.getBody().getLinks();
        assertNotNull("Missing 'links' attribute", links);
        assertThat("Missing 'self' attribute", links, hasKey(SELF_ATTRIBUTE_KEY));

        PaymentDTO paymentDTO = (PaymentDTO)this.paymentResponse.getBody().getData();
        URI expectedUri= URI.create(LOCALHOST + ":" + this.port + V1_PAYMENTS_API_PATH + "/" + paymentDTO.getId());
        assertThat(links, hasEntry(SELF_ATTRIBUTE_KEY, expectedUri));
    }

    @And("^it receives the resource URI in the Location header$")
    public void it_receives_a_valid_resource_uri_at_the_Location_header() {
        URI resourceLocation = this.paymentResponse.getHeaders().getLocation();
        Matcher matcher = buildPaymentUriMatcher(resourceLocation);

        assertTrue(matcher.matches());
        assertResourceIdIsValidUUID(matcher);
    }

    @And("^the resource version is (\\d+)$")
    public void the_resource_version_is(int version) {
        PaymentDTO paymentDTO = (PaymentDTO) paymentResponse.getBody().getData();

        assertThat(paymentDTO.getVersion(), is(version));
    }

    private void assertResourceIdIsValidUUID(Matcher matcher) {
        String resourceId = matcher.group(1);
        try {
            UUID.fromString(resourceId);
        } catch (Exception ex) {
            fail(String.format("The id in the Location header is not UUID compliant: %s", resourceId));
        }
    }

    private Matcher buildPaymentUriMatcher(URI resourceLocation) {
        Pattern pattern = Pattern.compile(LOCALHOST + ":" + this.port + V1_PAYMENTS_API_PATH + "/(.+)");
        return pattern.matcher(resourceLocation.toString());
    }
}