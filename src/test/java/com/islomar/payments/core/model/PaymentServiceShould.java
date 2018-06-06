package com.islomar.payments.core.model;

import com.islomar.payments.core.model.exceptions.PaymentNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.*;

import static com.islomar.payments.shared.ObjectMother.*;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;


public class PaymentServiceShould {

    @Mock private PaymentRepository paymentRepository;
    private PaymentService paymentService;

    @Before
    public void setUp() {
        initMocks(this);
        paymentService = new PaymentService(paymentRepository);
    }

    @Test
    public void persist_the_payment_when_saving_a_payment() {
        Payment dummyPayment = aDummyPayment();

        this.paymentService.save(dummyPayment);

        verify(this.paymentRepository).save(any(Payment.class));
    }

    @Test
    public void return_paymentId_with_UUID_format_when_saving_a_payment() {
        Payment dummyPayment = aDummyPayment();

        Payment createdPayment = this.paymentService.save(dummyPayment);

        this.verityPaymentIdIsUUDI(createdPayment);
    }

    @Test
    public void persist_the_payment_when_updating_an_existing_payment() {
        Payment existingPayment = aPaymentBuilder().id(ANY_VALID_PAYMENT_ID).build();
        given(paymentRepository.findById(ANY_VALID_PAYMENT_ID)).willReturn(Optional.of(existingPayment));

        existingPayment.setOrganisationId("another-organisation-id");
        this.paymentService.update(ANY_VALID_PAYMENT_ID, existingPayment);

        verify(this.paymentRepository).update(any(Payment.class));
    }

    @Test(expected = PaymentNotFoundException.class)
    public void throw_PaymentNotFoundException_when_updating_a_non_existing_payment() {
        given(paymentRepository.findById(ANY_NON_EXISTING_PAYMENT_ID)).willReturn(Optional.empty());

        Payment paymentToBeUpdated = aPaymentBuilder().id(ANY_NON_EXISTING_PAYMENT_ID).build();
        this.paymentService.update(ANY_NON_EXISTING_PAYMENT_ID, paymentToBeUpdated);
    }

    @Test
    public void delete_a_payment_if_it_exists() {
        given(paymentRepository.findById(ANY_VALID_PAYMENT_ID)).willReturn(Optional.of(aDummyPayment()));

        this.paymentService.delete(ANY_VALID_PAYMENT_ID);

        verify(this.paymentRepository).deleteById((ANY_VALID_PAYMENT_ID));
    }

    @Test(expected = PaymentNotFoundException.class)
    public void throw_PaymentNotFoundException_when_deleting_a_non_existing_payment() {
        given(paymentRepository.findById(ANY_NON_EXISTING_PAYMENT_ID)).willReturn(Optional.empty());

        this.paymentService.delete(ANY_NON_EXISTING_PAYMENT_ID);
    }

    @Test
    public void fetch_a_payment_if_it_exists() {
        Payment expectedFoundPayment = aDummyPayment();
        given(paymentRepository.findById(ANY_VALID_PAYMENT_ID)).willReturn(Optional.of(expectedFoundPayment));

        Payment foundPayment = this.paymentService.findById(ANY_VALID_PAYMENT_ID);

        verify(this.paymentRepository).findById((ANY_VALID_PAYMENT_ID));
        assertThat(foundPayment, is(expectedFoundPayment));
    }

    @Test(expected = PaymentNotFoundException.class)
    public void throw_PaymentNotFoundException_when_fetching_a_non_existing_payment() {
        given(paymentRepository.findById(ANY_NON_EXISTING_PAYMENT_ID)).willReturn(Optional.empty());

        this.paymentService.findById(ANY_NON_EXISTING_PAYMENT_ID);
    }

    @Test
    public void return_empty_list_when_fetching_all_payments_if_none_exist() {
        given(paymentRepository.findAll()).willReturn(Collections.emptyList());

        List<Payment> allPayments = this.paymentService.findAll();

        verify(this.paymentRepository).findAll();
        assertThat(allPayments, is(empty()));
    }

    @Test
    public void return_all_the_existing_payments() {
        given(paymentRepository.findAll()).willReturn(Arrays.asList(aDummyPayment(), aDummyPayment()));

        List<Payment> allPayments = this.paymentService.findAll();

        verify(this.paymentRepository).findAll();
        assertThat(allPayments, hasSize(2));
    }

    private void verityPaymentIdIsUUDI(Payment payment) {
        String paymentId = payment.getId();
        try {
            UUID.fromString(paymentId);
        } catch (Exception e) {
            fail(String.format("The payment id should be a UUID, but it was '%s'", paymentId));
        }
    }
}