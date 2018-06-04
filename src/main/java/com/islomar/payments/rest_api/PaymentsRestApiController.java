package com.islomar.payments.rest_api;

import com.islomar.payments.core.actions.CreateOnePayment;
import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.model.PaymentTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
public class PaymentsRestApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentsRestApiController.class);

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
    public ResponseEntity createOnePayment(HttpServletRequest request, @RequestBody PaymentTO paymentTO) {
        Payment payment = createOnePayment.execute(paymentTO);
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(request.getRequestURL() + "/" + payment.getId()));
        return new ResponseEntity<>(new CreateOnePaymentResponse(null, null), headers, CREATED);
    }

    private URL currentUrl(HttpServletRequest request) throws MalformedURLException {
        return new URL(request.getRequestURL().toString());
    }

    @ExceptionHandler({Exception.class})
    void handleInternalServerError(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
        LOGGER.error(request.getMethod() + " request: " + request.getRequestURL() + " raised " + ex);
        response.sendError(INTERNAL_SERVER_ERROR.value());
    }
}
