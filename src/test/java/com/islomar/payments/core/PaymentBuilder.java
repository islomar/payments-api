package com.islomar.payments.core;

import com.islomar.payments.core.model.Payment;

public class PaymentBuilder {

    private String paymentId = "any-payment-id";

    public static PaymentBuilder aPayment() {
        return new PaymentBuilder();
    }

    public PaymentBuilder withId(String id) {
        this.paymentId= id;
        return this;
    }

    public Payment build() {
        return new Payment(this.paymentId);
    }
}
