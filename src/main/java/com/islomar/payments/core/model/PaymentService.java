package com.islomar.payments.core.model;

import com.islomar.payments.core.model.exceptions.PaymentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentValidator validator;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, PaymentValidator validator) {

        this.paymentRepository = paymentRepository;
        this.validator = validator;
    }

    public Payment save(Payment payment) {
        String paymentId = this.generatePaymentId();
        payment.setId(paymentId);
        payment.validate(validator);
        this.paymentRepository.save(payment);
        return payment;
    }

    public Payment update(String paymentId, Payment paymentToBeUpdated) {
        Optional<Payment> foundPayment = this.paymentRepository.findById(paymentId);
        raiseExceptionIfPaymentNotFound(foundPayment);
        paymentToBeUpdated.setId(paymentId);
        paymentToBeUpdated.validate(validator);
        return this.paymentRepository.update(paymentToBeUpdated);
    }

    public Payment findById(String paymentId) {
        Optional<Payment> payment = this.paymentRepository.findById(paymentId);
        raiseExceptionIfPaymentNotFound(payment);
        return payment.get();
    }

    public List<Payment> findAll() {
        return this.paymentRepository.findAll();
    }

    public void delete(String paymentId) {
        Optional<Payment> payment = paymentRepository.findById(paymentId);
        raiseExceptionIfPaymentNotFound(payment);
        this.paymentRepository.deleteById(paymentId);
    }

    private String generatePaymentId() {
        return UUID.randomUUID().toString();
    }

    private void raiseExceptionIfPaymentNotFound(Optional<Payment> payment) {
        if (!payment.isPresent()) {
            throw new PaymentNotFoundException();
        }
    }
}
