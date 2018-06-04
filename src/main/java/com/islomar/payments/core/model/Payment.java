package com.islomar.payments.core.model;

public class Payment {
    private final String paymentId;
    private String type;
    private String organisationId;

    public Payment(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getId() {
        return this.paymentId;
    }
}
