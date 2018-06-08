package com.islomar.payments.integration;

import com.islomar.payments.core.actions.CreateOnePayment;
import com.islomar.payments.core.infrastructure.PaymentDTO;
import com.islomar.payments.core.infrastructure.PaymentMapper;
import com.islomar.payments.core.model.PaymentExternalValidator;
import com.islomar.payments.core.model.PaymentService;
import com.islomar.payments.core.model.exceptions.InvalidFieldError;
import com.islomar.payments.core.model.exceptions.InvalidPaymentException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static com.islomar.payments.shared.ObjectMother.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;


public class CreateOnePaymentIT {



    private CreateOnePayment createOnePayment;

    @Before
    public void setUp() {
        PaymentService paymentService = aPaymentService(anInMemoryPaymentRepository());
        PaymentExternalValidator paymentExternalValidator = aPaymentExternalValidator();
        PaymentMapper paymentMapper = aPaymentMapper();
        this.createOnePayment = new CreateOnePayment(paymentService, paymentExternalValidator, paymentMapper);
    }

    @Test
    public void when_saving_an_empty_payment_Then_throw_InvalidPaymentException() {
        PaymentDTO emptyPaymentDTO = anEmptyPaymentDTO();

        try {
            this.createOnePayment.execute(emptyPaymentDTO);

            fail("An InvalidPaymentException should have been thrown!");
        } catch (InvalidPaymentException ex) {
            List<InvalidFieldError> errors = ex.getErrors();
            assertThat(errors, is(not(empty())));
            assertThat(errors, hasItem(new InvalidFieldError("type", "must not be null")));
            assertThat(errors, hasItem(new InvalidFieldError("attributes", "must not be null")));
            assertThat(errors, hasItem(new InvalidFieldError("organisationId", "must not be blank")));
        } catch (Exception ex) {
            fail("An InvalidPaymentException should have been thrown!");
        }
    }

    @Test
    public void when_saving_a_payment_without_attributes_endToEndReference_Then_throw_InvalidPaymentException() throws IOException {
        PaymentDTO paymentDTO = aValidPaymentDTO();
        paymentDTO.getAttributes().setEndToEndReference(null);

        try {
            this.createOnePayment.execute(paymentDTO);

            fail("An InvalidPaymentException should have been thrown!");
        } catch (InvalidPaymentException ex) {
            List<InvalidFieldError> errors = ex.getErrors();
            assertThat(errors, contains(new InvalidFieldError("attributes.endToEndReference", "must not be blank")));
        } catch (Exception ex) {
            fail("An InvalidPaymentException should have been thrown!");
        }
    }
}
