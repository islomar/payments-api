package com.islomar.payments.core.model;

import com.islomar.payments.core.model.entities.PaymentAttributes;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private String id;
    private String type;
    private int version;
    private String organisationId;
    private PaymentAttributes attributes;

    public Payment(String id) {
        this.id = id;
    }
}
