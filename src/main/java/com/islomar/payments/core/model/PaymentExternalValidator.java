package com.islomar.payments.core.model;

import com.islomar.payments.core.infrastructure.PaymentDTO;
import com.islomar.payments.core.model.exceptions.InvalidPaymentException;
import org.springframework.stereotype.Service;

@Service
public class PaymentExternalValidator {

    /**
     * Here, being supported by additional Domain or Infrastructure services, I would validate against "out of this context boundary",
     * for example:
     *      * Fraud detection: contact an external WS.
     *      * Check that the bank ids are correct
     */
    public void validate(PaymentDTO paymentDTO) throws InvalidPaymentException {
        validateBeneficiaryParty(paymentDTO);
        validateDebtorParty(paymentDTO);
        validateSponsorParty(paymentDTO);
        // etc.
    }

    private void validateSponsorParty(PaymentDTO paymentDTO) {
        //TODO
    }

    private void validateDebtorParty(PaymentDTO paymentDTO) {
        //TODO
    }

    private void validateBeneficiaryParty(PaymentDTO paymentDTO) {
        //TODO
    }
}
