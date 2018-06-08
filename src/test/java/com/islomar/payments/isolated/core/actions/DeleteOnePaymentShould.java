package com.islomar.payments.isolated.core.actions;

import com.islomar.payments.core.actions.DeleteOnePayment;
import com.islomar.payments.core.model.PaymentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.islomar.payments.shared.ObjectMother.ANY_VALID_PAYMENT_ID;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class DeleteOnePaymentShould {

    @Mock
    private PaymentService paymentService;
    private DeleteOnePayment deleteOnePayment;

    @Before
    public void setUp() {
        initMocks(this);
        this.deleteOnePayment = new DeleteOnePayment(paymentService);
    }

    @Test
    public void delete_a_payment() {
        this.deleteOnePayment.execute(ANY_VALID_PAYMENT_ID);

        verify(this.paymentService).delete(ANY_VALID_PAYMENT_ID);
    }

}
