package com.islomar.payments.core.model.exceptions;

import java.util.List;

public class InvalidPaymentException extends PaymentException {

    private List<InvalidFieldError> errors;

    public InvalidPaymentException(List<InvalidFieldError> errors) {
        super();
        this.errors = errors;
    }

    public List<InvalidFieldError> getErrors() {
        return errors;
    }
}
