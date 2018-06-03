package com.islomar.payments.core.actions;

import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.model.PaymentService;
import com.islomar.payments.core.model.PaymentTO;
import com.islomar.payments.core.model.PaymentsRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.UUID;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;


public class PaymentServiceShould {

    @Mock private PaymentsRepository paymentsRepository;
    private PaymentTO paymentTO;
    private PaymentService paymentService;

    @Before
    public void setUp() {
        initMocks(this);
        paymentService = new PaymentService(paymentsRepository);
    }

    @Test
    public void saveValidPayment() {
        this.paymentService.save(new PaymentTO());

        verify(this.paymentsRepository).save(any(Payment.class));
    }

    @Test
    public void returnsPaymentIdWithUUIDformat() {
        Payment payment = this.paymentService.save(this.paymentTO);
        String paymentId = payment.getId();
        try {
            UUID.fromString(paymentId);
        } catch (Exception e) {
            fail(String.format("The payment id should be a UUID, but it was '%s'", paymentId));
        }
    }
}