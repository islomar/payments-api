package com.islomar.payments.web;

import com.islomar.payments.core.actions.CreateOnePayment;
import com.islomar.payments.core.actions.DeleteOnePayment;
import com.islomar.payments.core.actions.FetchAllPayments;
import com.islomar.payments.core.actions.FetchOnePayment;
import com.islomar.payments.core.infrastructure.PaymentTO;
import com.islomar.payments.core.model.exceptions.PaymentNotFoundException;
import com.islomar.payments.web.response.FetchAllPaymentsResponse;
import com.islomar.payments.web.response.FetchOrCreateOnePaymentResponse;
import com.islomar.payments.web.response.PaymentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
public class PaymentsRestApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentsRestApiController.class);

    private CreateOnePayment createOnePayment;
    private FetchOnePayment fetchOnePayment;
    private DeleteOnePayment deleteOnePayment;
    private FetchAllPayments fetchAllPayments;

    @Autowired
    public PaymentsRestApiController(CreateOnePayment createOnePayment, FetchOnePayment fetchOnePayment, DeleteOnePayment deleteOnePayment, FetchAllPayments fetchAllPayments) {

        this.createOnePayment = createOnePayment;
        this.fetchOnePayment = fetchOnePayment;
        this.deleteOnePayment = deleteOnePayment;
        this.fetchAllPayments = fetchAllPayments;
    }

    @RequestMapping("/")
    public String index() {
        return "The server is up and running!";
    }

    @GetMapping(value = "/v1/payments")
    @ResponseBody
    public FetchAllPaymentsResponse fetchAllPayments(HttpServletRequest request) {
        List<PaymentTO> allPayments = this.fetchAllPayments.execute();

        FetchAllPaymentsResponse fetchAllPaymentsResponse = new FetchAllPaymentsResponse(allPayments);
        fetchAllPaymentsResponse.addLink("self", currentUrl(request));
        return fetchAllPaymentsResponse;
    }

    @GetMapping(value = "/v1/payments/{paymentId}")
    @ResponseBody
    public FetchOrCreateOnePaymentResponse fetchOnePayment(HttpServletRequest request, @PathVariable String paymentId) {
        PaymentTO paymentTO = this.fetchOnePayment.execute(paymentId);

        URI paymentUri = URI.create(currentUrl(request).toString());
        FetchOrCreateOnePaymentResponse response = new FetchOrCreateOnePaymentResponse(paymentTO);
        fillResponseWithLinks(response, paymentUri);
        return response;
    }

    @PostMapping(value = "/v1/payments")
    @ResponseBody
    public ResponseEntity createOnePayment(HttpServletRequest request, @RequestBody PaymentTO inputPaymentTO) {
        PaymentTO createdPaymentTO = createOnePayment.execute(inputPaymentTO);

        URI paymentUri = buildPaymentURI(request, createdPaymentTO);
        FetchOrCreateOnePaymentResponse response = new FetchOrCreateOnePaymentResponse(createdPaymentTO);
        fillResponseWithLinks(response, paymentUri);

        HttpHeaders headers = generateHeadersWithLocation(paymentUri);

        return new ResponseEntity<>(response, headers, CREATED);
    }

    @DeleteMapping(value = "/v1/payments/{paymentId}")
    @ResponseBody
    public ResponseEntity deleteOnePayment(HttpServletRequest request, @PathVariable String paymentId) {
        this.deleteOnePayment.execute(paymentId);

        return ResponseEntity.noContent().build();
    }

    private URI currentUrl(HttpServletRequest request) {
        return URI.create(request.getRequestURL().toString());
    }

    private void fillResponseWithLinks(PaymentResponse response, URI paymentUri) {
        response.addLink("self", paymentUri);
    }

    private URI buildPaymentURI(HttpServletRequest request, PaymentTO paymentTO) {
        return URI.create(currentUrl(request) + "/" + paymentTO.getId());
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
    void handleNotFoundError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(NOT_FOUND.value());
    }
}