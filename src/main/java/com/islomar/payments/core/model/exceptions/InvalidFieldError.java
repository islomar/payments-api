package com.islomar.payments.core.model.exceptions;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class InvalidFieldError implements Serializable {

    private final String fieldName;
    private final String errorMessage;
}
