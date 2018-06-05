package com.islomar.payments.core.actions;

import com.islomar.payments.core.model.PaymentService;
import com.islomar.payments.core.infrastructure.PaymentTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FetchAllPayments {
    private final PaymentService paymentService;

    @Autowired
    public FetchAllPayments(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public List<PaymentTO> execute() {
        return paymentService.findAll();
    }
}
