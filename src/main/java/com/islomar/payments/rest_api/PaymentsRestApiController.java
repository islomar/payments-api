package com.islomar.payments.rest_api;

import com.islomar.payments.core.actions.CreateOnePayment;
import com.islomar.payments.core.actions.FetchOnePayment;
import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.model.PaymentTO;
import com.islomar.payments.core.model.PaymentsRepository;
import com.islomar.payments.core.model.exceptions.PaymentNotFoundException;
import com.islomar.payments.rest_api.response.FetchAllPaymentsResponse;
import com.islomar.payments.rest_api.response.FetchOrCreateOnePaymentResponse;
import com.islomar.payments.rest_api.response.PaymentResponse;
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
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
public class PaymentsRestApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentsRestApiController.class);

    private final CreateOnePayment createOnePayment;
    private final FetchOnePayment fetchOnePayment;

    @Autowired
    public PaymentsRestApiController(CreateOnePayment createOnePayment, FetchOnePayment fetchOnePayment) {

        this.createOnePayment = createOnePayment;
        this.fetchOnePayment = fetchOnePayment;
    }

    @RequestMapping("/")
    public String index() {
        return "The server is up and running!";
    }

    @GetMapping(value = "/v1/payments")
    @ResponseBody
    public FetchAllPaymentsResponse fetchAllPayments(HttpServletRequest request) {
        FetchAllPaymentsResponse fetchAllPaymentsResponse = new FetchAllPaymentsResponse(Collections.emptyList());
        fetchAllPaymentsResponse.addLink("self", currentUrl(request));
        return fetchAllPaymentsResponse;
    }

    @GetMapping(value = "/v1/payments/{paymentId}")
    @ResponseBody
    public FetchOrCreateOnePaymentResponse fetchOnePayment(HttpServletRequest request, @PathVariable String paymentId) throws MalformedURLException {
        LOGGER.info("Searching for paymentId {} {}", "xxx", paymentId);
        Payment payment = this.fetchOnePayment.execute(paymentId);
        URI paymentUri = URI.create(currentUrl(request) + "/" + payment.getId());
        FetchOrCreateOnePaymentResponse response = new FetchOrCreateOnePaymentResponse(new PaymentTO(paymentId,null, null, null));
        response.addLink("self", paymentUri);
        return response;
    }

    @PostMapping(value = "/v1/payments")
    @ResponseBody
    public ResponseEntity createOnePayment(HttpServletRequest request, @RequestBody PaymentTO inputPaymentTO) {
        PaymentTO createdPaymentTO = createOnePayment.execute(inputPaymentTO);
        System.out.println(String.format(">>>>>>> paymentId = %s", createdPaymentTO.getId()));

        URI paymentUri = URI.create(currentUrl(request) + "/" + createdPaymentTO.getId());
        FetchOrCreateOnePaymentResponse response = new FetchOrCreateOnePaymentResponse(new PaymentTO(createdPaymentTO.getId(), null, null, null));
        fillResponseWithLinks(response, paymentUri);
        HttpHeaders headers = generateHeadersWithLocation(paymentUri);

        return new ResponseEntity<>(response, headers, CREATED);
    }

    private URI currentUrl(HttpServletRequest request) {
        return URI.create(request.getRequestURL().toString());
    }

    private void fillResponseWithLinks(PaymentResponse response, URI paymentUri) {
        response.addLink("self", paymentUri);
    }

    private URI buildPaymentURI(HttpServletRequest request, Payment payment) {
        return URI.create(currentUrl(request) + "/" + payment.getId());
    }

    private HttpHeaders generateHeadersWithLocation(URI paymentUri) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(paymentUri);
        return headers;
    }

    @ExceptionHandler({Exception.class})
    void handleInternalServerError(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
        LOGGER.error("{} request: {} raised {}", request.getMethod(), request.getRequestURL(), ex);
        response.sendError(INTERNAL_SERVER_ERROR.value());
    }

    @ExceptionHandler({PaymentNotFoundException.class})
    void handleNotFoundError(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
        response.sendError(NOT_FOUND.value());
    }
}
