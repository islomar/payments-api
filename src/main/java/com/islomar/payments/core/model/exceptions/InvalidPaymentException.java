package com.islomar.payments.core.model.exceptions;

import java.io.Serializable;
import java.util.List;

public class InvalidPaymentException extends PaymentException implements Serializable {

    private final List<InvalidFieldError> errors;

    public InvalidPaymentException(List<InvalidFieldError> errors) {
        super();
        this.errors = errors;
    }

    public List<InvalidFieldError> getErrors() {
        return errors;
    }
}
