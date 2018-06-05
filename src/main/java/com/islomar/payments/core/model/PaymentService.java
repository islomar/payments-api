package com.islomar.payments.core.model;

import com.islomar.payments.core.model.exceptions.PaymentNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final PaymentsRepository paymentsRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public PaymentService(PaymentsRepository paymentsRepository) {

        this.paymentsRepository = paymentsRepository;
    }

    public PaymentTO save(PaymentTO paymentTO) {
        String paymentId = this.generatePaymentId();
        Payment payment = new Payment(paymentId);
        this.paymentsRepository.save(payment);

        PaymentTO createdPaymentTO = modelMapper.map(payment, PaymentTO.class);
        createdPaymentTO.getId();
        //modelMapper.validate();
        //TODO: ignore some attributes: http://modelmapper.org/user-manual/property-mapping/
        return createdPaymentTO;
    }

    public PaymentTO findById(String paymentId) {
        Optional<Payment> payment = this.paymentsRepository.findById(paymentId);
        raiseExceptionIfPaymentNotFound(payment);
        return new PaymentTO(paymentId, null, null, null);
    }

    public List<PaymentTO> findAll() {
        List<Payment> allPayments = this.paymentsRepository.findAll();
        return allPayments.stream()
                .map(payment -> modelMapper.map(payment, PaymentTO.class))
                .collect(Collectors.toList());
    }

    public void delete(String paymentId) {
        Optional<Payment> payment = paymentsRepository.findById(paymentId);
        raiseExceptionIfPaymentNotFound(payment);
        this.paymentsRepository.deleteById(paymentId);
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
