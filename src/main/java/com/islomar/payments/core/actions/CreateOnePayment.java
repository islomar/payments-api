package com.islomar.payments.core.actions;

import com.islomar.payments.core.model.PaymentService;
import com.islomar.payments.core.infrastructure.PaymentTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateOnePayment {

    private final PaymentService paymentService;

    @Autowired
    public CreateOnePayment(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public PaymentTO execute(PaymentTO paymentTO) {
        return paymentService.save(paymentTO);
    }


}
