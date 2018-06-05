package com.islomar.payments.web.response;

import com.islomar.payments.core.infrastructure.PaymentDTO;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FetchAllPaymentsResponse implements PaymentResponse {

    private List<PaymentDTO> data;
    private Map<String, URI> links;

    public FetchAllPaymentsResponse(){}

    public FetchAllPaymentsResponse(List<PaymentDTO> data) {

        this.data = data;
        this.links = new HashMap<>();
    }

    @Override
    public void addLink(String linkName, URI linkUri) {
        links.put(linkName, linkUri);
    }

    @Override
    public List<PaymentDTO> getData() {
        return data;
    }

    @Override
    public Map<String, URI> getLinks() {
        return Collections.unmodifiableMap(this.links);
    }
}
