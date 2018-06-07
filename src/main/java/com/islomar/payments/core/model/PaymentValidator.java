package com.islomar.payments.core.model;

import com.islomar.payments.core.model.exceptions.InvalidFieldError;
import com.islomar.payments.core.model.exceptions.InvalidPaymentException;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PaymentValidator {
    private final Validator validator;

    public PaymentValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public void validate(Object object) {
        Set<ConstraintViolation<Object>> violations = this.validator.validate(object);
        if (!violations.isEmpty()) {
            List<InvalidFieldError> invalidFieldErrors = convert(violations);
            throw new InvalidPaymentException(invalidFieldErrors);
        }
    }

    private List<InvalidFieldError> convert(Set<ConstraintViolation<Object>> violations) {
        return violations.stream().map(this::convertToInvalidFieldError).collect(Collectors.toList());
    }

    private InvalidFieldError convertToInvalidFieldError(ConstraintViolation<Object> violation) {
        return new InvalidFieldError(violation.getPropertyPath().toString(), violation.getMessage());
    }
}
