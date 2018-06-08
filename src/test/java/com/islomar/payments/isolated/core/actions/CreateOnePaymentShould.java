package com.islomar.payments.isolated.core.actions;

import com.islomar.payments.core.actions.CreateOnePayment;
import com.islomar.payments.core.infrastructure.PaymentDTO;
import com.islomar.payments.core.infrastructure.PaymentMapper;
import com.islomar.payments.core.model.Payment;
import com.islomar.payments.core.model.PaymentExternalValidator;
import com.islomar.payments.core.model.PaymentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.islomar.payments.shared.ObjectMother.anEmptyPayment;
import static com.islomar.payments.shared.ObjectMother.anEmptyPaymentDTO;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class CreateOnePaymentShould {

    @Mock private PaymentService paymentService;
    @Mock private PaymentExternalValidator paymentExternalValidator;
    @Mock private PaymentMapper paymentMapper;
    private final Payment dummyPayment = anEmptyPayment();
    private final Payment dummyCreatedPayment = anEmptyPayment();
    private final PaymentDTO dummyPaymentDTO = anEmptyPaymentDTO();
    private final PaymentDTO dummyCreatedPaymentDTO = anEmptyPaymentDTO();
    private CreateOnePayment createOnePayment;

    @Before
    public void setUp() {
        initMocks(this);
        this.createOnePayment = new CreateOnePayment(paymentService, paymentExternalValidator, paymentMapper);
    }

    @Test
    public void save_a_valid_payment() {
        givenPaymentMapperMapsCorrectly(dummyPayment, dummyPaymentDTO, dummyCreatedPayment, dummyCreatedPaymentDTO);
        given(paymentService.save(dummyPayment)).willReturn(dummyPayment);

        this.createOnePayment.execute(dummyPaymentDTO);

        verify(this.paymentService).save(dummyPayment);
    }

    @Test
    public void return_the_created_payment() {
        givenPaymentMapperMapsCorrectly(dummyPayment, dummyPaymentDTO, dummyCreatedPayment, dummyCreatedPaymentDTO);
        given(paymentService.save(dummyPayment)).willReturn(dummyCreatedPayment);

        PaymentDTO createdPaymentDTO = this.createOnePayment.execute(dummyPaymentDTO);

        assertThat(createdPaymentDTO, is(dummyPaymentDTO));
    }

    private void givenPaymentMapperMapsCorrectly(Payment dummyPayment, PaymentDTO dummyPaymentDTO, Payment dummyCreatedPayment, PaymentDTO dummyCreatedPaymentDTO) {
        given(paymentMapper.fromDTO(dummyPaymentDTO)).willReturn(dummyPayment);
        given(paymentMapper.toDTO(dummyCreatedPayment)).willReturn(dummyCreatedPaymentDTO);
    }
}
