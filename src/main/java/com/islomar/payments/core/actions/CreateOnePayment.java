package com.islomar.payments.core.actions;

import com.islomar.payments.core.infrastructure.PaymentDTO;
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

    public PaymentDTO execute(PaymentDTO paymentDTO) {
        Payment payment = fromDTO(paymentDTO);
        Payment createdPayment = paymentService.save(payment);
        return toDTO(createdPayment);
    }


}
