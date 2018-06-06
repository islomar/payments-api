package com.islomar.payments.core.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.UUID;

import static com.islomar.payments.shared.ObjectMother.aPayment;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;


public class PaymentServiceShould {

    @Mock private PaymentRepository paymentRepository;
    private PaymentService paymentService;

    @Before
    public void setUp() {
        initMocks(this);
        paymentService = new PaymentService(paymentRepository);
    }

    @Test
    public void save_a_valid_payment() {
        Payment dummyPayment = aPayment();

        this.paymentService.save(dummyPayment);

        verify(this.paymentRepository).save(any(Payment.class));
    }

    @Test
    public void return_paymentId_with_UUID_format() {
        Payment dummyPayment = aPayment();

        Payment createdPayment = this.paymentService.save(dummyPayment);

        this.verityPaymentIdIsUUDI(createdPayment);
    }

    private void verityPaymentIdIsUUDI(Payment payment) {
        String paymentId = payment.getId();
        try {
            UUID.fromString(paymentId);
        } catch (Exception e) {
            fail(String.format("The payment id should be a UUID, but it was '%s'", paymentId));
        }
    }
}