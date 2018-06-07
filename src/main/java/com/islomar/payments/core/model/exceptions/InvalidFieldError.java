package com.islomar.payments.core.model.exceptions;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class InvalidFieldError {

    private final String fieldName;
    private final String errorMessage;
}
