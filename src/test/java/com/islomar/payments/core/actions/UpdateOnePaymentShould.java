package com.islomar.payments.core.actions;

import com.islomar.payments.core.infrastructure.PaymentDTO;
import com.islomar.payments.core.infrastructure.PaymentMapper;
import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.model.PaymentExternalValidator;
import com.islomar.payments.core.model.PaymentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.islomar.payments.shared.ObjectMother.ANY_VALID_PAYMENT_ID;
import static com.islomar.payments.shared.ObjectMother.aDummyPayment;
import static com.islomar.payments.shared.ObjectMother.aDummyPaymentDTO;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class UpdateOnePaymentShould {

    @Mock private PaymentService paymentService;
    @Mock private PaymentExternalValidator paymentExternalValidator;
    @Mock private PaymentMapper paymentMapper;
    private PaymentDTO dummyPaymentDTO = aDummyPaymentDTO();
    private UpdateOnePayment updateOnePayment;

    @Before
    public void setUp() {
        initMocks(this);
        this.updateOnePayment = new UpdateOnePayment(paymentService, paymentExternalValidator, paymentMapper);
    }

    @Test
    public void update_a_valid_payment() {
        Payment dummyPayment = aDummyPayment();
        Payment dummyUpdatedPayment = aDummyPayment();
        given(paymentMapper.fromDTO(dummyPaymentDTO)).willReturn(dummyPayment);
        given(paymentService.update(eq(ANY_VALID_PAYMENT_ID), any(Payment.class))).willReturn(dummyUpdatedPayment);

        this.updateOnePayment.execute(ANY_VALID_PAYMENT_ID, dummyPaymentDTO);

        verify(this.paymentService).update(ANY_VALID_PAYMENT_ID, dummyPayment);
    }

    @Test
    public void validate_the_payment_against_external_invariants() {
        given(paymentService.update(anyString(), any(Payment.class))).willReturn(aDummyPayment());

        this.updateOnePayment.execute(ANY_VALID_PAYMENT_ID, this.dummyPaymentDTO);

        verify(this.paymentExternalValidator).validate(this.dummyPaymentDTO);
    }

    @Test
    public void return_the_updated_paymentDTO() {
        Payment dummyPayment = aDummyPayment();
        Payment dummyUpdatedPayment = aDummyPayment();
        given(paymentMapper.fromDTO(dummyPaymentDTO)).willReturn(dummyPayment);
        given(paymentMapper.toDTO(dummyUpdatedPayment)).willReturn(dummyPaymentDTO);
        given(paymentService.update(eq(ANY_VALID_PAYMENT_ID), any(Payment.class))).willReturn(dummyUpdatedPayment);

        PaymentDTO updatedPaymentDTO = this.updateOnePayment.execute(ANY_VALID_PAYMENT_ID, dummyPaymentDTO);

        assertThat(updatedPaymentDTO, is(dummyPaymentDTO));
    }
}
