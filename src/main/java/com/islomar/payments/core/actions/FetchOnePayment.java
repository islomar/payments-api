package com.islomar.payments.core.actions;

import com.islomar.payments.core.infrastructure.PaymentDTO;
import com.islomar.payments.core.infrastructure.PaymentMapper;
import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.model.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FetchOnePayment {

    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;

    @Autowired
    public FetchOnePayment(PaymentService paymentService, PaymentMapper paymentMapper) {
        this.paymentService = paymentService;
        this.paymentMapper = paymentMapper;
    }

    public PaymentDTO execute(String paymentId) {
        Payment payment = paymentService.findById(paymentId);
        return this.paymentMapper.toDTO(payment);
    }

}
