package com.islomar.payments.isolated.core.actions;

import com.islomar.payments.core.actions.FetchAllPayments;
import com.islomar.payments.core.infrastructure.PaymentDTO;
import com.islomar.payments.core.infrastructure.PaymentMapper;
import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.model.PaymentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static com.islomar.payments.shared.ObjectMother.anEmptyPayment;
import static com.islomar.payments.shared.ObjectMother.anEmptyPaymentDTO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class FetchAllPaymentsShould {

    @Mock
    private PaymentService paymentService;
    @Mock private PaymentMapper paymentMapper;
    private FetchAllPayments fetchAllPayments;

    @Before
    public void setUp() {
        initMocks(this);
        this.fetchAllPayments = new FetchAllPayments(paymentService, paymentMapper);
    }

    @Test
    public void fetch_all_existing_payments() {
        Payment dummyPayment = anEmptyPayment();
        PaymentDTO dummyPaymentDTO = anEmptyPaymentDTO();
        given(paymentService.findAll()).willReturn(Arrays.asList(anEmptyPayment(), anEmptyPayment(), anEmptyPayment()));
        given(paymentMapper.toDTO(dummyPayment)).willReturn(dummyPaymentDTO);

        List<PaymentDTO> allPaymentDTOs = this.fetchAllPayments.execute();

        verify(this.paymentService).findAll();
        verify(this.paymentMapper, times(3)).toDTO(any(Payment.class));
        assertThat(allPaymentDTOs, hasSize(3));
    }
}
