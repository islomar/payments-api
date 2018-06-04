package com.islomar.payments.core.actions;

import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.model.PaymentService;
import com.islomar.payments.core.model.PaymentTO;
import com.islomar.payments.core.model.PaymentsRepository;
import com.islomar.payments.core.model.exceptions.PaymentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FetchOnePayment {

    private final PaymentService paymentService;

    @Autowired
    public FetchOnePayment(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public Payment execute(String paymentId) {
        return paymentService.findById(paymentId);
    }

}
