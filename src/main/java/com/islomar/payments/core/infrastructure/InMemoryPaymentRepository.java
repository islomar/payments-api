package com.islomar.payments.core.infrastructure;

import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.model.PaymentRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryPaymentRepository implements PaymentRepository {
    private Map<String, Payment> paymentStore = new HashMap<>();

    @Override
    public void save(Payment payment) {
        paymentStore.put(payment.getId(), payment);
    }

    @Override
    public Optional<Payment> findById(String paymentId) {
        return Optional.ofNullable(paymentStore.get(paymentId));
    }

    @Override
    public void deleteById(String paymentId) {
        this.paymentStore.remove(paymentId);
    }

    @Override
    public List<Payment> findAll() {
        return new ArrayList(this.paymentStore.values());
    }

}
