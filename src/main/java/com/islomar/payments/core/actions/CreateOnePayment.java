package com.islomar.payments.core.actions;

import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.model.PaymentService;
import com.islomar.payments.core.model.PaymentTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateOnePayment {

    private final PaymentService paymentService;

    @Autowired
    public CreateOnePayment(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public Payment execute(PaymentTO paymentTO) {
        return paymentService.save(paymentTO);
    }


}
