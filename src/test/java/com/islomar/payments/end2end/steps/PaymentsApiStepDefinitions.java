package com.islomar.payments.end2end.steps;

import com.islomar.payments.core.model.Payment;
import com.islomar.payments.end2end.SpringBootBaseFeatureTest;
import com.islomar.payments.rest_api.CreateOnePaymentResponse;
import com.islomar.payments.rest_api.FetchAllPaymentsResponse;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

public class PaymentsApiStepDefinitions extends SpringBootBaseFeatureTest {

    private static final String LOCALHOST = "http://localhost";
    private static final String V1_PAYMENTS_API_PATH = "/v1/payments";
    private static final String SELF_ATTRIBUTE_KEY = "self";
    private ResponseEntity<FetchAllPaymentsResponse> response;
    private ResponseEntity<CreateOnePaymentResponse> createResponse;

    @Given("^no payments exist$")
    public void noPaymentsExist() {
    }

    @When("^the client calls GET /v1/payments/(\\S+)$")
    public void the_client_calls_GET_one_payment(String paymentId) {
        this.response = fetchOnePayment(paymentId);
    }

    @When("^the client calls GET /v1/payments$")
    public void the_client_calls_GET_payments() {
        this.response = fetchAllPayments();
    }

    @When("^the client calls POST /v1/payments$")
    public void theClientCallsPOSTVPayments() {
        this.createResponse = createOnePayment();
    }

    @Then("^no payments are returned$")
    public void noPaymentsAreReturned() {
        List<Payment> data = response.getBody().getData();
        assertThat(data, empty());
    }

    @And("^it receives response status code of (\\d+)$")
    public void the_client_receives_response_status_code_of(int httpStatusCodeValue) {
        assertThat(this.response.getStatusCodeValue(), is(httpStatusCodeValue));
    }

    @And("^it receives response status code of (\\d+) 2$")
    public void the_client_receives_response_status_code_of_2(int httpStatusCodeValue) {
        assertThat(this.createResponse.getStatusCodeValue(), is(httpStatusCodeValue));
    }

    @And("^it receives response body text \"([^\"]*)\"$")
    public void theClientReceivesResponseBodyText(String text) {
        assertThat(this.response.getBody(), is(text));
    }

    @And("^the response has JSON format$")
    public void the_response_has_JSON_format() {
        assertThat(this.response.getHeaders().getContentType().getType(), is(APPLICATION_JSON.getType()));
    }

    @And("^the links attribute contains a self to ([^\"]*)$")
    public void the_links_attribute_contains_a_self_to_all_payments_uri(String path) throws MalformedURLException {
        Map<String, URL> links = response.getBody().getLinks();
        assertNotNull("Missing 'links' attribute", links);
        assertThat("Missing 'self' attribute", links, hasKey(SELF_ATTRIBUTE_KEY));

        URL expectedUrl= new URL(LOCALHOST + ":" + this.port + path);
        assertThat(links, hasEntry(SELF_ATTRIBUTE_KEY, expectedUrl));
    }

    @And("^it receives the resource URI in the Location header$")
    public void it_receives_a_valid_resource_uri_at_the_Location_header() {
        URI resourceLocation = this.createResponse.getHeaders().getLocation();
        Matcher matcher = buildPaymentUriMatcher(resourceLocation);

        assertTrue(matcher.matches());
        assertResourceIdIsValidUUID(matcher);
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