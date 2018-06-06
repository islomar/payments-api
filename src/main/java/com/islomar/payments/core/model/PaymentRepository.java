package com.islomar.payments.core.model;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository {
    void save(Payment payment);

    Payment update(Payment payment);

    Optional<Payment> findById(String paymentId);

    void deleteById(String paymentId);

    List<Payment> findAll();
}
