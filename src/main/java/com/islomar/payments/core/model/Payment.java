package com.islomar.payments.core.model;

public class Payment {
    private final String paymentId;

    public Payment(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getId() {
        return this.paymentId;
    }
}
