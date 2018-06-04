package com.islomar.payments.core.actions;

import com.islomar.payments.core.model.PaymentService;
import com.islomar.payments.core.model.PaymentTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FetchOnePayment {

    private final PaymentService paymentService;

    @Autowired
    public FetchOnePayment(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public PaymentTO execute(String paymentId) {
        return paymentService.findById(paymentId);
    }

}
