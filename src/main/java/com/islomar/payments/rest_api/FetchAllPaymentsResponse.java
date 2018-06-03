package com.islomar.payments.rest_api;

import com.islomar.payments.core.model.Payment;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FetchAllPaymentsResponse {

    private List<Payment> data;
    private Map<String, URL> links;

    public FetchAllPaymentsResponse(){}

    public FetchAllPaymentsResponse(List<Payment> data) {

        this.data = data;
        this.links = new HashMap<>();
    }

    public void addLink(String linkName, URL linkUri) {
        links.put(linkName, linkUri);
    }

    public List<Payment> getData() {
        return data;
    }

    public Map<String, URL> getLinks() {
        return Collections.unmodifiableMap(this.links);
    }
}
