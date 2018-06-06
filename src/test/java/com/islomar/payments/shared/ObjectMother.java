package com.islomar.payments.shared;

import com.islomar.payments.core.model.Payment;

public class ObjectMother {

    public static Payment aPayment() {
        return Payment.builder().build();
    }
}
