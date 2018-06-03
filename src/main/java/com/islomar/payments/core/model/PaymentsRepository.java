package com.islomar.payments.core.model;

import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRepository {
    void save(Payment payment);
}
