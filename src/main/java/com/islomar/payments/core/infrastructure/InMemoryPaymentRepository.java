package com.islomar.payments.core.infrastructure;

import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.model.PaymentsRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryPaymentRepository implements PaymentsRepository {
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

}
