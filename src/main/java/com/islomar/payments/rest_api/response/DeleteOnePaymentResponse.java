package com.islomar.payments.rest_api.response;

import java.net.URI;
import java.util.Map;

public class DeleteOnePaymentResponse implements PaymentResponse {

    public DeleteOnePaymentResponse() {
    }

    @Override
    public Object getData() {
        return null;
    }

    @Override
    public Map<String, URI> getLinks() {
        return null;
    }

    @Override
    public void addLink(String linkName, URI linkUri) {

    }
}
