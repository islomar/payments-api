package com.islomar.payments.core.model;

import com.islomar.payments.core.infrastructure.PaymentTO;
import com.islomar.payments.core.model.exceptions.PaymentNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {

        this.paymentRepository = paymentRepository;
    }

    public PaymentTO save(PaymentTO paymentTO) {
        String paymentId = this.generatePaymentId();
        Payment payment = modelMapper.map(paymentTO, Payment.class);
        payment.setId(paymentId);
        this.paymentRepository.save(payment);

        PaymentTO createdPaymentTO = modelMapper.map(payment, PaymentTO.class);
        createdPaymentTO.getId();
        //modelMapper.validate();
        //TODO: ignore some attributes: http://modelmapper.org/user-manual/property-mapping/
        return createdPaymentTO;
    }

    public Payment findById(String paymentId) {
        Optional<Payment> payment = this.paymentRepository.findById(paymentId);
        raiseExceptionIfPaymentNotFound(payment);
        return payment.get();
    }

    public List<PaymentTO> findAll() {
        List<Payment> allPayments = this.paymentRepository.findAll();
        return allPayments.stream()
                .map(payment -> modelMapper.map(payment, PaymentTO.class))
                .collect(Collectors.toList());
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
