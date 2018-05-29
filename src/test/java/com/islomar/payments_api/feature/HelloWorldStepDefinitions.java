package com.islomar.payments_api.feature;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class HelloWorldStepDefinitions extends SpringBootBaseFeatureTest {

    private ResponseEntity<String> response;

    @When("the client calls GET \\/hello-world")
    public void the_client_calls_GET_hello_world() {
        this.response = helloWorld();
    }

    @Then("the client receives response status code of {int}")
    public void the_client_receives_response_status_code_of(int httpStatusCode) {
        assertThat(this.response.getStatusCode(), is(HttpStatus.valueOf(httpStatusCode)));
    }

    @And("^the client receives response body text \"([^\"]*)\"$")
    public void theClientReceivesResponseBodyText(String text) throws Throwable {
        assertThat(this.response.getBody(), is(text));
    }
}