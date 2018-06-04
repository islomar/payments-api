package com.islomar.payments.core.model;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentsRepository {
    void save(Payment payment);

    Optional<Payment> findById(String paymentId);
}
