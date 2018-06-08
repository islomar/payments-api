package com.islomar.payments.integration;

import com.islomar.payments.core.actions.UpdateOnePayment;
import com.islomar.payments.core.infrastructure.PaymentDTO;
import com.islomar.payments.core.infrastructure.PaymentMapper;
import com.islomar.payments.core.model.Payment;
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


public class UpdateOnePaymentIT {

    private PaymentService paymentService;
    private UpdateOnePayment updateOnePayment;

    @Before
    public void setUp() {
        this.paymentService = aPaymentService(anInMemoryPaymentRepository());
        PaymentExternalValidator paymentExternalValidator = aPaymentExternalValidator();
        PaymentMapper paymentMapper = aPaymentMapper();
        this.updateOnePayment = new UpdateOnePayment(this.paymentService, paymentExternalValidator, paymentMapper);
    }

    @Test
    public void when_updating_an_existing_payment_with_invalid_chargesInformation_Then_throw_InvalidPaymentException() throws IOException {
        Payment existingPayment = givenAnExistingPayment();

        try {
            PaymentDTO paymentDTO = aValidPaymentDTO();
            paymentDTO.getAttributes().setChargesInformation(null);

            this.updateOnePayment.execute(existingPayment.getId(), paymentDTO);

            fail("An InvalidPaymentException should have been thrown!");
        } catch (InvalidPaymentException ex) {
            List<InvalidFieldError> errors = ex.getErrors();
            assertThat(errors, contains(new InvalidFieldError("attributes.chargesInformation", "must not be null")));
        } catch (Exception ex) {
            fail("An InvalidPaymentException should have been thrown!");
        }
    }

    private Payment givenAnExistingPayment() throws IOException {
        return this.paymentService.save(aValidPayment());
    }
}
