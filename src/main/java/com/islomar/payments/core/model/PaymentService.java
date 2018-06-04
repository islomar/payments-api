package com.islomar.payments.core.model;

import com.islomar.payments.core.model.exceptions.PaymentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    public PaymentTO save(PaymentTO paymentTO) {
        //Payment payment = modelMapper.map(paymentTO, Payment.class);
        String paymentId = this.generatePaymentId();
        Payment payment = new Payment(paymentId);
        this.paymentsRepository.save(payment);
        PaymentTO createdPaymentTO = new PaymentTO(paymentId, null, null, null);
        return createdPaymentTO;
    }

    public PaymentTO findById(String paymentId) {
        Optional<Payment> payment = this.paymentsRepository.findById(paymentId);
        raiseExceptionIfPaymentNotFound(payment);
        return new PaymentTO(paymentId, null, null, null);
    }

    public List<PaymentTO> findAll() {
        List<Payment> allPayments = this.paymentsRepository.findAll();
        for (Payment payment : allPayments) {
            System.out.println(String.format(">>>>>>>>>>>>>>>>>>>>>>> %s", payment));
        }
        //TODO convert to PaymentTO
        return new ArrayList<>();
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
