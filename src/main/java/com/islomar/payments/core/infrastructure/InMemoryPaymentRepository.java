package com.islomar.payments.core.infrastructure;

import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.model.PaymentRepository;
import jdk.nashorn.internal.ir.annotations.Immutable;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryPaymentRepository implements PaymentRepository {
    private Map<String, Payment> paymentStore = new ConcurrentHashMap<>();

    @Override
    public void save(Payment payment) {
        paymentStore.put(payment.getId(), payment);
    }

    @Override
    public synchronized Payment update(Payment newPayment) {
        String paymentId = newPayment.getId();
        updateVersion(paymentId, newPayment);
        paymentStore.put(paymentId, newPayment);
        return SerializationUtils.clone(newPayment);
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

    private void updateVersion(String paymentId, Payment newPayment) {
        Payment existingPayment = paymentStore.get(paymentId);
        newPayment.setVersion(existingPayment.getVersion() + 1);
    }

}
