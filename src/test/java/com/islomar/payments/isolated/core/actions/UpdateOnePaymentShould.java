package com.islomar.payments.isolated.core.actions;

import com.islomar.payments.core.actions.UpdateOnePayment;
import com.islomar.payments.core.infrastructure.PaymentDTO;
import com.islomar.payments.core.infrastructure.PaymentMapper;
import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.model.PaymentExternalValidator;
import com.islomar.payments.core.model.PaymentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.islomar.payments.shared.ObjectMother.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class UpdateOnePaymentShould {

    @Mock private PaymentService paymentService;
    @Mock private PaymentExternalValidator paymentExternalValidator;
    @Mock private PaymentMapper paymentMapper;
    private final Payment dummyPayment = anEmptyPayment();
    private final Payment dummyUpdatedPayment = anEmptyPayment();
    private final PaymentDTO dummyPaymentDTO = anEmptyPaymentDTO();
    private final PaymentDTO dummyUpdatedPaymentDTO = anEmptyPaymentDTO();
    private UpdateOnePayment updateOnePayment;

    @Before
    public void setUp() {
        initMocks(this);
        this.updateOnePayment = new UpdateOnePayment(paymentService, paymentExternalValidator, paymentMapper);
    }

    @Test
    public void update_a_valid_payment() {
        givenPaymentMapperMapsCorrectly(dummyPayment, dummyPaymentDTO, dummyUpdatedPayment, dummyUpdatedPaymentDTO);

        this.updateOnePayment.execute(ANY_VALID_PAYMENT_ID, dummyPaymentDTO);

        verify(this.paymentService).update(ANY_VALID_PAYMENT_ID, dummyPayment);
    }

    @Test
    public void validate_the_payment_against_external_invariants() {
        given(paymentService.update(ANY_VALID_PAYMENT_ID, dummyPayment)).willReturn(dummyUpdatedPayment);

        this.updateOnePayment.execute(ANY_VALID_PAYMENT_ID, this.dummyPaymentDTO);

        verify(this.paymentExternalValidator).validate(this.dummyPaymentDTO);
    }

    @Test
    public void return_the_updated_paymentDTO() {
        givenPaymentMapperMapsCorrectly(dummyPayment, dummyPaymentDTO, dummyUpdatedPayment, dummyUpdatedPaymentDTO);
        given(paymentService.update(ANY_VALID_PAYMENT_ID, dummyPayment)).willReturn(dummyUpdatedPayment);

        PaymentDTO updatedPaymentDTO = this.updateOnePayment.execute(ANY_VALID_PAYMENT_ID, dummyPaymentDTO);

        assertThat(updatedPaymentDTO, is(dummyUpdatedPaymentDTO));
    }

    private void givenPaymentMapperMapsCorrectly(Payment dummyPayment, PaymentDTO dummyPaymentDTO, Payment dummyUpdatedPayment, PaymentDTO dummyUpdatedPaymentDTO) {
        given(paymentMapper.fromDTO(dummyPaymentDTO)).willReturn(dummyPayment);
        given(paymentMapper.toDTO(dummyUpdatedPayment)).willReturn(dummyUpdatedPaymentDTO);
    }
}
