package com.islomar.payments.web;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.islomar.payments.core.actions.CreateOnePayment;
import com.islomar.payments.core.actions.DeleteOnePayment;
import com.islomar.payments.core.actions.FetchAllPayments;
import com.islomar.payments.core.actions.FetchOnePayment;
import com.islomar.payments.core.infrastructure.PaymentDTO;
import com.islomar.payments.core.model.exceptions.PaymentNotFoundException;
import com.islomar.payments.web.response.FetchAllPaymentsResponse;
import com.islomar.payments.web.response.FetchOrCreateOnePaymentResponse;
import com.islomar.payments.web.response.PaymentResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public PaymentsRestApiController(CreateOnePayment createOnePayment, FetchOnePayment fetchOnePayment, DeleteOnePayment deleteOnePayment, FetchAllPayments fetchAllPayments) {

        this.createOnePayment = createOnePayment;
        this.fetchOnePayment = fetchOnePayment;
        this.deleteOnePayment = deleteOnePayment;
        this.fetchAllPayments = fetchAllPayments;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @RequestMapping("/")
    public String index() {
        return "The server is up and running!";
    }

    @GetMapping(value = "/v1/payments")
    @ResponseBody
    public FetchAllPaymentsResponse fetchAllPayments(HttpServletRequest request) {
        List<PaymentDTO> allPayments = this.fetchAllPayments.execute();

        FetchAllPaymentsResponse fetchAllPaymentsResponse = new FetchAllPaymentsResponse(allPayments);
        fetchAllPaymentsResponse.addLink("self", currentUrl(request));
        return fetchAllPaymentsResponse;
    }

    @GetMapping(value = "/v1/payments/{paymentId}")
    @ResponseBody
    public FetchOrCreateOnePaymentResponse fetchOnePayment(HttpServletRequest request, @PathVariable String paymentId) {
        PaymentDTO paymentDTO = this.fetchOnePayment.execute(paymentId);

        URI paymentUri = URI.create(currentUrl(request).toString());
        FetchOrCreateOnePaymentResponse response = new FetchOrCreateOnePaymentResponse(paymentDTO);
        fillResponseWithLinks(response, paymentUri);
        return response;
    }

    @PostMapping(value = "/v1/payments")
    @ResponseBody
    public ResponseEntity createOnePayment(HttpServletRequest request, @Valid @RequestBody NewPaymentCommand newPaymentCommand) {
        System.out.println(String.format(">>>>>>>>>> newPaymentCommand: %s", newPaymentCommand));
        PaymentDTO inputPaymentDTO = modelMapper.map(newPaymentCommand, PaymentDTO.class);
        System.out.println(String.format(">>>>>>>>>> inputPaymentDTO: %s", inputPaymentDTO));

        PaymentDTO createdPaymentDTO = createOnePayment.execute(inputPaymentDTO);

        System.out.println(String.format(">>>>>>>>>> createdPaymentDTO: %s", createdPaymentDTO));
        URI paymentUri = buildPaymentURI(request, createdPaymentDTO);
        FetchOrCreateOnePaymentResponse response = new FetchOrCreateOnePaymentResponse(createdPaymentDTO);
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

    private URI buildPaymentURI(HttpServletRequest request, PaymentDTO paymentDTO) {
        return URI.create(currentUrl(request) + "/" + paymentDTO.getId());
    }

    private HttpHeaders generateHeadersWithLocation(URI paymentUri) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(paymentUri);
        return headers;
    }
}
