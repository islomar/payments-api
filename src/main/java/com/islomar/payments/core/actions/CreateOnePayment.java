package com.islomar.payments.core.actions;

import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.model.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateOnePayment {

    private final PaymentsRepository paymentsRepository;

    @Autowired
    public CreateOnePayment(PaymentsRepository paymentsRepository) {

        this.paymentsRepository = paymentsRepository;
    }

    public void execute(Payment payment) {
        this.paymentsRepository.save(payment);
    }
}
