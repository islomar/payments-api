package com.islomar.payments.isolated.core.infrastructure;

import com.islomar.payments.core.infrastructure.InMemoryPaymentRepository;
import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.model.PaymentRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.islomar.payments.shared.ObjectMother.aPaymentBuilder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PaymentRepositoryShould {

    private List<PaymentRepository> allPaymentRepositoryImplementations;

    @Before
    public void setUp() {
        allPaymentRepositoryImplementations = Arrays.asList(new InMemoryPaymentRepository());
    }

    @Test
    public void create_payment_with_initial_version_0() {
        String paymentId = generateRandomId();
        for (PaymentRepository paymentRepository : allPaymentRepositoryImplementations) {
            paymentRepository.save(aPaymentBuilder().id(paymentId).build());

            Optional<Payment> createdPayment = paymentRepository.findById(paymentId);
            assertThat(createdPayment.get().getVersion(), is(0));
        }
    }

    @Test
    public void update_payment_version_after_any_resource_update() {
        String paymentId = generateRandomId();
        for (PaymentRepository paymentRepository : allPaymentRepositoryImplementations) {
            paymentRepository.save(aPaymentBuilder().id(paymentId).build());

            Payment newPayment = aPaymentBuilder().id(paymentId).organisationId("updated-organisation-id").build();
            paymentRepository.update(newPayment);
            Payment updatedPayment = paymentRepository.update(newPayment);

            assertThat(updatedPayment.getVersion(), is(2));
        }
    }

    @Test
    public void fetch_all_existing_payments() {
        for (PaymentRepository paymentRepository : allPaymentRepositoryImplementations) {
            Payment payment1 = aPaymentBuilder().id(generateRandomId()).build();
            Payment payment2 = aPaymentBuilder().id(generateRandomId()).build();
            Payment payment3 = aPaymentBuilder().id(generateRandomId()).build();
            paymentRepository.save(payment1);
            paymentRepository.save(payment2);
            paymentRepository.save(payment3);

            List<Payment> allPayments = paymentRepository.findAll();

            assertThat(allPayments, hasSize(3));
            assertThat(allPayments, containsInAnyOrder(payment1, payment2, payment3));
        }
    }

    @Test
    public void fetch_one_existing_payment() {
        for (PaymentRepository paymentRepository : allPaymentRepositoryImplementations) {
            Payment payment1 = aPaymentBuilder().id(generateRandomId()).build();
            Payment payment2 = aPaymentBuilder().id(generateRandomId()).build();
            Payment payment3 = aPaymentBuilder().id(generateRandomId()).build();
            paymentRepository.save(payment1);
            paymentRepository.save(payment2);
            paymentRepository.save(payment3);

            Optional<Payment> foundPayment = paymentRepository.findById(payment2.getId());

            assertThat(foundPayment.get(), is(payment2));
        }
    }

    @Test
    public void delete_one_existing_payment() {
        for (PaymentRepository paymentRepository : allPaymentRepositoryImplementations) {
            Payment payment1 = aPaymentBuilder().id(generateRandomId()).build();
            Payment payment2 = aPaymentBuilder().id(generateRandomId()).build();
            Payment payment3 = aPaymentBuilder().id(generateRandomId()).build();
            paymentRepository.save(payment1);
            paymentRepository.save(payment2);
            paymentRepository.save(payment3);

            paymentRepository.deleteById(payment2.getId());

            List<Payment> allPayments = paymentRepository.findAll();
            assertThat(allPayments, containsInAnyOrder(payment1, payment3));
        }
    }

    private String generateRandomId() {
        return UUID.randomUUID().toString();
    }
}
