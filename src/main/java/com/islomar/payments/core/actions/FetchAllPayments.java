package com.islomar.payments.core.actions;

import com.islomar.payments.core.infrastructure.PaymentDTO;
import com.islomar.payments.core.infrastructure.PaymentMapper;
import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.model.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FetchAllPayments {

    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;

    @Autowired
    public FetchAllPayments(PaymentService paymentService, PaymentMapper paymentMapper) {
        this.paymentService = paymentService;
        this.paymentMapper = paymentMapper;
    }

    public List<PaymentDTO> execute() {
        List<Payment> allPayments = paymentService.findAll();
        return allPayments.stream()
                .map(this.paymentMapper::toDTO)
                .collect(Collectors.toList());
    }
}
