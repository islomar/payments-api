package com.islomar.payments.core.actions;

import com.islomar.payments.core.infrastructure.PaymentDTO;
import com.islomar.payments.core.infrastructure.PaymentMapper;
import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.model.PaymentExternalValidator;
import com.islomar.payments.core.model.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateOnePayment {

    private final PaymentService paymentService;
    private final PaymentExternalValidator paymentExternalValidator;
    private final PaymentMapper paymentMapper;

    @Autowired
    public UpdateOnePayment(PaymentService paymentService, PaymentExternalValidator paymentExternalValidator, PaymentMapper paymentMapper) {
        this.paymentService = paymentService;
        this.paymentExternalValidator = paymentExternalValidator;
        this.paymentMapper = paymentMapper;
    }

    public PaymentDTO execute(String paymentId, PaymentDTO paymentDTO) {
        Payment payment = convertToPayment(paymentDTO);
        Payment updatedPayment = paymentService.update(paymentId, payment);
        return this.paymentMapper.toDTO(updatedPayment);
    }

    private Payment convertToPayment(PaymentDTO paymentDTO) {
        this.paymentExternalValidator.validate(paymentDTO);
        return this.paymentMapper.fromDTO(paymentDTO);
    }
}
