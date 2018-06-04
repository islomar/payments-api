package com.islomar.payments.core.actions;

import com.islomar.payments.core.model.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteOnePayment {
    private final PaymentService paymentService;

    @Autowired
    public DeleteOnePayment(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void execute(String paymentId) {
         paymentService.delete(paymentId);
    }
}
