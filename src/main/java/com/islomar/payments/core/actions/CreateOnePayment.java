package com.islomar.payments.core.actions;

import com.islomar.payments.core.infrastructure.PaymentDTO;
import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.model.PaymentExternalValidator;
import com.islomar.payments.core.model.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateOnePayment extends PaymentAction {

    private final PaymentService paymentService;
    private final PaymentExternalValidator paymentExternalValidator;

    @Autowired
    public CreateOnePayment(PaymentService paymentService, PaymentExternalValidator paymentExternalValidator) {
        this.paymentService = paymentService;
        this.paymentExternalValidator = paymentExternalValidator;
    }

    public PaymentDTO execute(PaymentDTO paymentDTO) {
        this.paymentExternalValidator.validate(paymentDTO);
        Payment payment = fromDTO(paymentDTO);
        Payment createdPayment = paymentService.save(payment);
        return toDTO(createdPayment);
    }




}
