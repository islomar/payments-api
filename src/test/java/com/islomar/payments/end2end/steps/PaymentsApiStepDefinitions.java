package com.islomar.payments.end2end.steps;

import com.islomar.payments.api.ApiResponseLinks;
import com.islomar.payments.api.FetchAllPaymentsResponse;
import com.islomar.payments.core.Payment;
import com.islomar.payments.end2end.SpringBootBaseFeatureTest;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsMapContaining;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;

public class PaymentsApiStepDefinitions extends SpringBootBaseFeatureTest {

    private static final String LOCALHOST = "http://localhost";
    private static final String SELF_ATTRIBUTE_KEY = "self";
    private ResponseEntity<FetchAllPaymentsResponse> response;

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

    @Then("^no payments are returned$")
    public void noPaymentsAreReturned() {
        List<Payment> data = response.getBody().getData();
        assertThat(data, empty());
    }

    @And("^the client receives response status code of (\\d+)$")
    public void the_client_receives_response_status_code_of(int httpStatusCodeValue) {
        assertThat(this.response.getStatusCodeValue(), is(httpStatusCodeValue));
    }

    @And("^the client receives response body text \"([^\"]*)\"$")
    public void theClientReceivesResponseBodyText(String text) {
        assertThat(this.response.getBody(), is(text));
    }

    @And("^the response has JSON format$")
    public void the_response_has_JSON_format() {
        assertThat(this.response.getHeaders().getContentType().getType(), is(APPLICATION_JSON.getType()));
    }

    @And("^the links attribute contains a self to ([^\"]*)$")
    public void the_links_attribute_contains_a_self_to_all_payments_uri(String path) throws MalformedURLException {
        ApiResponseLinks apiResponseLinks = response.getBody().getLinks();
        assertNotNull("Missing 'links' attribute", apiResponseLinks);
        assertThat("Missing 'self' attribute", apiResponseLinks.getLinks(), hasKey(SELF_ATTRIBUTE_KEY));

        URL expectedUrl= new URL(LOCALHOST + ":" + this.port + path);
        assertThat(apiResponseLinks.getLinks(), hasEntry(SELF_ATTRIBUTE_KEY, expectedUrl));
    }
}