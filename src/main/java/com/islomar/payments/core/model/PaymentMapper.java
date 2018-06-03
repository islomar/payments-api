package com.islomar.payments.core.model;

public class PaymentMapper {

    public Payment convertToEntity(PaymentTO paymentTO) {
        return new Payment(null);
    }

    public PaymentTO convertTo(Payment payment) {
        return new PaymentTO();
    }
}
