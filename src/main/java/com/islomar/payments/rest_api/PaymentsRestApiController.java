package com.islomar.payments.rest_api;

import com.islomar.payments.core.actions.CreateOnePayment;
import com.islomar.payments.core.actions.DeleteOnePayment;
import com.islomar.payments.core.actions.FetchOnePayment;
import com.islomar.payments.core.model.PaymentTO;
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
import java.net.URI;
import java.util.Collections;

import static org.springframework.http.HttpStatus.*;

@RestController
public class PaymentsRestApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentsRestApiController.class);

    private final CreateOnePayment createOnePayment;
    private final FetchOnePayment fetchOnePayment;
    private final DeleteOnePayment deleteOnePayment;

    @Autowired
    public PaymentsRestApiController(CreateOnePayment createOnePayment, FetchOnePayment fetchOnePayment, DeleteOnePayment deleteOnePayment) {

        this.createOnePayment = createOnePayment;
        this.fetchOnePayment = fetchOnePayment;
        this.deleteOnePayment = deleteOnePayment;
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
    public FetchOrCreateOnePaymentResponse fetchOnePayment(HttpServletRequest request, @PathVariable String paymentId) {
        PaymentTO paymentTO = this.fetchOnePayment.execute(paymentId);

        URI paymentUri = buildPaymentURI(request, paymentTO);
        FetchOrCreateOnePaymentResponse response = new FetchOrCreateOnePaymentResponse(new PaymentTO(paymentId,null, null, null));
        fillResponseWithLinks(response, paymentUri);
        return response;
    }

    @PostMapping(value = "/v1/payments")
    @ResponseBody
    public ResponseEntity createOnePayment(HttpServletRequest request, @RequestBody PaymentTO inputPaymentTO) {
        PaymentTO createdPaymentTO = createOnePayment.execute(inputPaymentTO);

        URI paymentUri = buildPaymentURI(request, createdPaymentTO);
        FetchOrCreateOnePaymentResponse response = new FetchOrCreateOnePaymentResponse(new PaymentTO(createdPaymentTO.getId(), null, null, null));
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
