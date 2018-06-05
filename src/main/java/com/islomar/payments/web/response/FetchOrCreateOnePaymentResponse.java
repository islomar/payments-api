package com.islomar.payments.web.response;

import com.islomar.payments.core.infrastructure.PaymentTO;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FetchOrCreateOnePaymentResponse implements PaymentResponse {

    private PaymentTO data;
    private Map<String, URI> links;

    public FetchOrCreateOnePaymentResponse(){}

    public FetchOrCreateOnePaymentResponse(PaymentTO data) {

        this.data = data;
        this.links = new HashMap<>();
    }

    @Override
    public void addLink(String linkName, URI linkUri) {
        links.put(linkName, linkUri);
    }

    @Override
    public PaymentTO getData() {
        return data;
    }

    @Override
    public Map<String, URI> getLinks() {
        return Collections.unmodifiableMap(this.links);
    }
}