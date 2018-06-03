package com.islomar.payments.api;

import com.islomar.payments.core.Payment;

import java.util.List;

public class FetchAllPaymentsResponse {

    private List<Payment> data;
    private ApiResponseLinks links;

    public FetchAllPaymentsResponse(){}

    public FetchAllPaymentsResponse(List<Payment> data, ApiResponseLinks links) {

        this.data = data;
        this.links = links;
    }

    public List<Payment> getData() {
        return data;
    }

    public ApiResponseLinks getLinks() {
        return links;
    }
}
