package com.islomar.payments.core;

import com.islomar.payments.core.actions.CreateOnePayment;
import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.model.PaymentsRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;


public class CreateOnePaymentShould {

    @Mock private PaymentsRepository paymentsRepository;
    @Mock private Payment payment;
    private CreateOnePayment createOnePayment;

    @Before
    public void setUp() {
        initMocks(this);
        createOnePayment = new CreateOnePayment(paymentsRepository);
    }

    @Test
    public void saveValidPayment() {
        this.createOnePayment.execute(this.payment);

        verify(this.paymentsRepository).save(payment);
    }
}
