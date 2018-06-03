package com.islomar.payments.core.actions;

import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.model.PaymentsRepository;

public class CreateOnePayment {

    private final PaymentsRepository paymentsRepository;

    public CreateOnePayment(PaymentsRepository paymentsRepository) {

        this.paymentsRepository = paymentsRepository;
    }

    public void execute(Payment payment) {
        System.out.println(this.paymentsRepository);
        this.paymentsRepository.save(payment);
    }
}
