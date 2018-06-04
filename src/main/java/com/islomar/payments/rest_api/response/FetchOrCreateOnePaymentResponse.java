package com.islomar.payments.rest_api.response;

import com.islomar.payments.core.model.PaymentTO;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FetchOrCreateOnePaymentResponse implements PaymentResponse {

    private PaymentTO data;
    private Map<String, URL> links;

    public FetchOrCreateOnePaymentResponse(){}

    public FetchOrCreateOnePaymentResponse(PaymentTO data) {

        this.data = data;
        this.links = new HashMap<>();
    }

    public void addLink(String linkName, URL linkUri) {
        links.put(linkName, linkUri);
    }

    @Override
    public PaymentTO getData() {
        return data;
    }

    @Override
    public Map<String, URL> getLinks() {
        return Collections.unmodifiableMap(this.links);
    }
}