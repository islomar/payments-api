package com.islomar.payments.web;

import com.islomar.payments.core.actions.*;
import com.islomar.payments.core.infrastructure.PaymentDTO;
import com.islomar.payments.web.response.FetchAllPaymentsResponse;
import com.islomar.payments.web.response.OnePaymentResponse;
import com.islomar.payments.web.response.PaymentResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
public class PaymentsRestApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentsRestApiController.class);

    private final CreateOnePayment createOnePayment;
    private final FetchOnePayment fetchOnePayment;
    private final DeleteOnePayment deleteOnePayment;
    private final FetchAllPayments fetchAllPayments;
    private final UpdateOnePayment updateOnePayment;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public PaymentsRestApiController(CreateOnePayment createOnePayment, FetchOnePayment fetchOnePayment, DeleteOnePayment deleteOnePayment, FetchAllPayments fetchAllPayments, UpdateOnePayment updateOnePayment) {

        this.createOnePayment = createOnePayment;
        this.fetchOnePayment = fetchOnePayment;
        this.deleteOnePayment = deleteOnePayment;
        this.fetchAllPayments = fetchAllPayments;
        this.updateOnePayment = updateOnePayment;
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
    public OnePaymentResponse fetchOnePayment(HttpServletRequest request, @PathVariable String paymentId) {
        PaymentDTO paymentDTO = this.fetchOnePayment.execute(paymentId);

        URI paymentUri = URI.create(currentUrl(request).toString());
        OnePaymentResponse response = new OnePaymentResponse(paymentDTO);
        fillResponseWithLinks(response, paymentUri);
        return response;
    }

    @PostMapping(value = "/v1/payments")
    @ResponseBody
    public ResponseEntity createOnePayment(HttpServletRequest request, @Valid @RequestBody UpsertPaymentCommand upsertPaymentCommand) {
        PaymentDTO inputPaymentDTO = modelMapper.map(upsertPaymentCommand, PaymentDTO.class);

        PaymentDTO createdPaymentDTO = createOnePayment.execute(inputPaymentDTO);

        URI paymentUri = buildPaymentURI(request, createdPaymentDTO);
        OnePaymentResponse response = new OnePaymentResponse(createdPaymentDTO);
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

    @PutMapping(value = "/v1/payments/{paymentId}")
    @ResponseBody
    public ResponseEntity fullUpdateOnePayment(HttpServletRequest request, @PathVariable String paymentId, @Valid @RequestBody UpsertPaymentCommand updatePaymentCommand) {
        PaymentDTO inputPaymentDTO = modelMapper.map(updatePaymentCommand, PaymentDTO.class);
        PaymentDTO updatedPaymentDTO = this.updateOnePayment.execute(paymentId, inputPaymentDTO);

        OnePaymentResponse response = new OnePaymentResponse(updatedPaymentDTO);
        URI paymentURI = buildPaymentURI(request, updatedPaymentDTO);
        HttpHeaders headers = generateHeadersWithLocation(paymentURI);

        return new ResponseEntity<>(response, headers, OK);
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
