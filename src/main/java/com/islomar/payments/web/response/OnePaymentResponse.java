package com.islomar.payments.web.response;

import com.islomar.payments.core.infrastructure.PaymentDTO;
import lombok.ToString;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ToString
public class OnePaymentResponse implements PaymentResponse {

    private PaymentDTO data;
    private Map<String, URI> links;

    public OnePaymentResponse(){}

    public OnePaymentResponse(PaymentDTO data) {

        this.data = data;
        this.links = new HashMap<>();
    }

    @Override
    public void addLink(String linkName, URI linkUri) {
        links.put(linkName, linkUri);
    }

    @Override
    public PaymentDTO getData() {
        return data;
    }

    @Override
    public Map<String, URI> getLinks() {
        return Collections.unmodifiableMap(this.links);
    }
}