package com.islomar.payments.core.actions;

import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.model.PaymentService;
import com.islomar.payments.core.model.PaymentTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class CreateOnePaymentShould {

    @Mock private PaymentService paymentService;
    @Mock private PaymentTO paymentTO;
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

    @Test
    public void returnsTheCreatedPayment() {
        given(paymentService.save(this.paymentTO)).willReturn(this.paymentTO);

        PaymentTO createdPayment = this.createOnePayment.execute(this.paymentTO);

        assertThat(createdPayment, is(this.paymentTO));
    }
}
