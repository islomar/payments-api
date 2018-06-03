package com.islomar.payments.core.infrastructure;

import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.model.PaymentsRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryPaymentRepository implements PaymentsRepository {
    private List<Payment> paymentStore = new ArrayList<>();

    @Override
    public void save(Payment payment) {
        paymentStore.add(payment);
    }

}
