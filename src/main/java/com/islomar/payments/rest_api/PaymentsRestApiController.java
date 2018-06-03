package com.islomar.payments.rest_api;

import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.actions.CreateOnePayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Collections;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
public class PaymentsRestApiController {

    private final CreateOnePayment createOnePayment;

    @Autowired
    public PaymentsRestApiController(CreateOnePayment createOnePayment) {

        this.createOnePayment = createOnePayment;
    }

    @RequestMapping("/")
    public String index() {
        return "The server is up and running!";
    }

    @GetMapping(value = "/v1/payments")
    @ResponseBody
    public FetchAllPaymentsResponse fetchAllPayments(HttpServletRequest request) throws MalformedURLException {
        FetchAllPaymentsResponse fetchAllPaymentsResponse = new FetchAllPaymentsResponse(Collections.emptyList());
        fetchAllPaymentsResponse.addLink("self", currentUrl(request));
        return fetchAllPaymentsResponse;
    }

    @PostMapping(value = "/v1/payments")
    @ResponseBody
    public ResponseEntity createOnePayment(HttpServletRequest request, @RequestBody Payment payment) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("http://localhost:9000/v1/payments/1"));
        return new ResponseEntity<>(new CreateOnePaymentResponse(null, null), headers, CREATED);
    }

    private URL currentUrl(HttpServletRequest request) throws MalformedURLException {
        return new URL(request.getRequestURL().toString());
    }

    @ExceptionHandler({Exception.class})
    void handleInternalServerError(HttpServletResponse response) throws IOException {
        response.sendError(INTERNAL_SERVER_ERROR.value());
    }
}
