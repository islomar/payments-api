package com.islomar.payments.core.actions;

import com.islomar.payments.core.infrastructure.PaymentTO;
import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.model.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateOnePayment extends PaymentAction {

    private final PaymentService paymentService;

    @Autowired
    public CreateOnePayment(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public PaymentTO execute(PaymentTO paymentTO) {
        Payment payment = fromDTO(paymentTO);
        Payment createdPayment = paymentService.save(payment);
        return toDTO(createdPayment);
    }


}
