package com.islomar.payments.core.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentsRepository paymentsRepository;

//    @Autowired
//    protected ModelMapper modelMapper;

    @Autowired
    public PaymentService(PaymentsRepository paymentsRepository) {

        this.paymentsRepository = paymentsRepository;
    }

    public Payment save(PaymentTO paymentTO) {
        //Payment payment = modelMapper.map(paymentTO, Payment.class);
        String paymentId = this.generatePaymentId();
        Payment payment = new Payment(paymentId);
        this.paymentsRepository.save(payment);
        return payment;
    }



    private String generatePaymentId() {
        return UUID.randomUUID().toString();
    }
}
