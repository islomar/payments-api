package com.islomar.payments.rest_api.response;

import com.islomar.payments.core.model.Payment;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FetchAllPaymentsResponse implements PaymentResponse {

    private List<Payment> data;
    private Map<String, URI> links;

    public FetchAllPaymentsResponse(){}

    public FetchAllPaymentsResponse(List<Payment> data) {

        this.data = data;
        this.links = new HashMap<>();
    }

    @Override
    public void addLink(String linkName, URI linkUri) {
        links.put(linkName, linkUri);
    }

    @Override
    public List<Payment> getData() {
        return data;
    }

    @Override
    public Map<String, URI> getLinks() {
        return Collections.unmodifiableMap(this.links);
    }
}
