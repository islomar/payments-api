package com.islomar.payments.core.model;

import com.islomar.payments.core.model.exceptions.InvalidFieldError;
import com.islomar.payments.core.model.exceptions.InvalidPaymentException;
import com.islomar.payments.core.model.payment_attributes.PaymentAttributes;
import com.islomar.payments.shared.ObjectMother;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.MockitoAnnotations.initMocks;

public class PaymentValidatorShould {

    private PaymentValidator paymentValidator;

    @Before
    public void setUp() {
        initMocks(this);
        paymentValidator = new PaymentValidator();
    }

    @Test
    public void a_payment_without_any_mandatory_field_filled_is_invalid() {
        try {
            Payment payment = ObjectMother.anEmptyPayment();
            payment.validate(this.paymentValidator);
            fail("An InvalidPaymentException should have been thrown!");
        } catch (InvalidPaymentException e) {
            List<InvalidFieldError> errors = e.getErrors();
            assertThat(errors, hasSize(4));
            assertThat(errors, hasItem(new InvalidFieldError("type", "must not be null")));
            assertThat(errors, hasItem(new InvalidFieldError("attributes", "must not be null")));
            assertThat(errors, hasItem(new InvalidFieldError("id", "must not be blank")));
            assertThat(errors, hasItem(new InvalidFieldError("organisationId", "must not be blank")));
        }
    }

    @Test
    public void a_payment_with_all_the_mandatories_fields_filled_is_valid() {
        try {
            Payment payment = ObjectMother.aValidPayment();
            payment.validate(this.paymentValidator);
        } catch (Exception e) {
            fail("No exception should have been thrown!");
        }
    }

    @Test
    public void a_payment_attributes_without_any_mandatory_field_filled_is_invalid() {
        try {
            PaymentAttributes paymentAttributes = PaymentAttributes.builder().build();
            Payment payment = ObjectMother.aPaymentBuilder().id("1").type(PaymentType.PAYMENT).version(1).organisationId("1").attributes(paymentAttributes).build();
            payment.validate(this.paymentValidator);
            fail("An InvalidPaymentException should have been thrown!");
        } catch (InvalidPaymentException e) {
            List<InvalidFieldError> errors = e.getErrors();
            assertThat(errors, hasItem(new InvalidFieldError("attributes.beneficiaryParty", "must not be null")));
            assertThat(errors, hasItem(new InvalidFieldError("attributes.amount", "must not be null")));
        }
    }
}
