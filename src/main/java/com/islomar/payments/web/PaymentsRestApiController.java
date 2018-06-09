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
    private static final String REST_API_V1_PATH = "/v1/payments";

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

    @GetMapping(value = REST_API_V1_PATH)
    @ResponseBody
    public FetchAllPaymentsResponse fetchAllPayments(HttpServletRequest request) {
        LOGGER.debug("GET request to {}", request.getRequestURL());
        List<PaymentDTO> allPayments = this.fetchAllPayments.execute();

        return buildFetchAllPaymentsResponse(request, allPayments);
    }

    @GetMapping(value = REST_API_V1_PATH +"/{paymentId}")
    @ResponseBody
    public OnePaymentResponse fetchOnePayment(HttpServletRequest request, @PathVariable String paymentId) {
        LOGGER.debug("GET request to {} with paymentId {}", request.getRequestURL(), paymentId);
        PaymentDTO paymentDTO = this.fetchOnePayment.execute(paymentId);

        return buildOnePaymentResponse(request, paymentDTO);
    }

    @PostMapping(value = REST_API_V1_PATH)
    @ResponseBody
    public ResponseEntity createOnePayment(HttpServletRequest request, @Valid @RequestBody UpsertPaymentCommand upsertPaymentCommand) {
        LOGGER.debug("POST request to {} with body {}", request.getRequestURL(), upsertPaymentCommand);
        PaymentDTO inputPaymentDTO = modelMapper.map(upsertPaymentCommand, PaymentDTO.class);

        PaymentDTO createdPaymentDTO = createOnePayment.execute(inputPaymentDTO);

        URI paymentUri = buildPaymentURI(request, createdPaymentDTO);
        OnePaymentResponse response = new OnePaymentResponse(createdPaymentDTO);
        fillResponseWithLinks(response, paymentUri);
        HttpHeaders headers = generateHeadersWithLocation(paymentUri);

        return new ResponseEntity<>(response, headers, CREATED);
    }

    @DeleteMapping(value = REST_API_V1_PATH + "/{paymentId}")
    @ResponseBody
    public ResponseEntity deleteOnePayment(HttpServletRequest request, @PathVariable String paymentId) {
        LOGGER.debug("DELETE request for deleting one payment resource with id '{}'", paymentId);
        this.deleteOnePayment.execute(paymentId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = REST_API_V1_PATH + "/{paymentId}")
    @ResponseBody
    public OnePaymentResponse fullUpdateOnePayment(HttpServletRequest request, @PathVariable String paymentId, @Valid @RequestBody UpsertPaymentCommand updatePaymentCommand) {
        LOGGER.debug("PUT request to {} with body {}", request.getRequestURL(), updatePaymentCommand);
        PaymentDTO inputPaymentDTO = modelMapper.map(updatePaymentCommand, PaymentDTO.class);

        PaymentDTO updatedPaymentDTO = this.updateOnePayment.execute(paymentId, inputPaymentDTO);

        return buildOnePaymentResponse(request, updatedPaymentDTO);
    }

    private URI buildPaymentURI(HttpServletRequest request, PaymentDTO paymentDTO) {
        return URI.create(currentUrl(request) + "/" + paymentDTO.getId());
    }

    private HttpHeaders generateHeadersWithLocation(URI paymentUri) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(paymentUri);
        return headers;
    }

    private OnePaymentResponse buildOnePaymentResponse(HttpServletRequest request, PaymentDTO paymentDTO) {
        URI paymentUri = URI.create(currentUrl(request).toString());
        OnePaymentResponse response = new OnePaymentResponse(paymentDTO);
        fillResponseWithLinks(response, paymentUri);
        return response;
    }

    private FetchAllPaymentsResponse buildFetchAllPaymentsResponse(HttpServletRequest request, List<PaymentDTO> allPayments) {
        FetchAllPaymentsResponse fetchAllPaymentsResponse = new FetchAllPaymentsResponse(allPayments);
        fetchAllPaymentsResponse.addLink("self", currentUrl(request));
        return fetchAllPaymentsResponse;
    }

    private URI currentUrl(HttpServletRequest request) {
        return URI.create(request.getRequestURL().toString());
    }

    private void fillResponseWithLinks(PaymentResponse response, URI paymentUri) {
        response.addLink("self", paymentUri);
    }
}
