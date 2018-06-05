package com.islomar.payments.core.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private String id;
    private String type;
    private String organisationId;
    private PaymentAttributes attributes;

    public Payment(String id) {
        this.id = id;
    }
}
