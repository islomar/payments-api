package com.islomar.payments.core.actions;

import com.islomar.payments.core.model.PaymentService;
import com.islomar.payments.core.model.PaymentTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;


public class CreateOnePaymentShould {

    @Mock private PaymentService paymentService;
    @Mock private PaymentTO paymentTO;
    @Mock private PaymentTO payment;
    private CreateOnePayment createOnePayment;

    @Before
    public void setUp() {
        initMocks(this);
        this.createOnePayment = new CreateOnePayment(paymentService);
    }

    @Test
    public void saveValidPayment() {
        this.createOnePayment.execute(this.paymentTO);

        verify(this.paymentService).save(this.paymentTO);
    }
}
