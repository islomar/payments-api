package com.islomar.payments.core.actions;

import com.islomar.payments.core.infrastructure.PaymentDTO;
import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.model.PaymentExternalValidator;
import com.islomar.payments.core.model.PaymentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Spy;

import static com.islomar.payments.shared.ObjectMother.aDummyPayment;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class CreateOnePaymentShould {

    @Mock private PaymentService paymentService;
    @Mock private PaymentExternalValidator paymentExternalValidator;
    @Spy private PaymentDTO paymentDTO;
    private CreateOnePayment createOnePayment;

    @Before
    public void setUp() {
        initMocks(this);
        this.createOnePayment = new CreateOnePayment(paymentService, paymentExternalValidator);
    }

    @Test
    public void save_a_valid_payment() {
        Payment dummyPayment = aDummyPayment();
        given(paymentService.save(any(Payment.class))).willReturn(dummyPayment);

        this.createOnePayment.execute(this.paymentDTO);

        verify(this.paymentService).save(dummyPayment);
    }

    @Test
    public void return_the_created_payment() {
        Payment dummyPayment = aDummyPayment();
        given(paymentService.save(any(Payment.class))).willReturn(dummyPayment);

        PaymentDTO createdPaymentDTO = this.createOnePayment.execute(this.paymentDTO);

        assertThat(createdPaymentDTO, is(this.paymentDTO));
    }
}
