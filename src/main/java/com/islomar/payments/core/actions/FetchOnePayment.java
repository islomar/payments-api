package com.islomar.payments.core.actions;

import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.model.PaymentService;
import com.islomar.payments.core.infrastructure.PaymentTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FetchOnePayment extends PaymentAction {

    private final PaymentService paymentService;

    @Autowired
    public FetchOnePayment(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public PaymentTO execute(String paymentId) {
        Payment payment = paymentService.findById(paymentId);
        return toDTO(payment);
    }

}
