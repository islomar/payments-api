package com.islomar.payments.core.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentType {

    PAYMENT("Payment");

    private String description;

    @JsonValue
    public String description() {
        return this.description;
    }
}
