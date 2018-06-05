package com.islomar.payments.core.actions;

import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.model.PaymentService;
import com.islomar.payments.core.infrastructure.PaymentTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FetchAllPayments extends PaymentAction {
    private final PaymentService paymentService;

    @Autowired
    public FetchAllPayments(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public List<PaymentTO> execute() {
        List<Payment> allPayments = paymentService.findAll();
        return allPayments.stream()
                .map(payment -> toDTO(payment))
                .collect(Collectors.toList());
    }
}
