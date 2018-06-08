package com.islomar.payments.isolated.core.actions;

import com.islomar.payments.core.actions.FetchOnePayment;
import com.islomar.payments.core.infrastructure.PaymentDTO;
import com.islomar.payments.core.infrastructure.PaymentMapper;
import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.model.PaymentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.islomar.payments.shared.ObjectMother.ANY_VALID_PAYMENT_ID;
import static com.islomar.payments.shared.ObjectMother.anEmptyPayment;
import static com.islomar.payments.shared.ObjectMother.anEmptyPaymentDTO;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class FetchOnePaymentShould {

    @Mock private PaymentService paymentService;
    @Mock private PaymentMapper paymentMapper;
    private FetchOnePayment fetchOnePayment;

    @Before
    public void setUp() {
        initMocks(this);
        this.fetchOnePayment = new FetchOnePayment(paymentService, paymentMapper);
    }

    @Test
    public void fetch_one_existing_payment() {
        Payment dummyPayment = anEmptyPayment();
        PaymentDTO dummyPaymentDTO = anEmptyPaymentDTO();
        given(paymentService.findById(ANY_VALID_PAYMENT_ID)).willReturn(dummyPayment);
        given(paymentMapper.toDTO(dummyPayment)).willReturn(dummyPaymentDTO);

        PaymentDTO foundPaymentDTO = this.fetchOnePayment.execute(ANY_VALID_PAYMENT_ID);

        verify(this.paymentService).findById(ANY_VALID_PAYMENT_ID);
        assertThat(foundPaymentDTO, is(dummyPaymentDTO));
    }
}
