package com.islomar.payments.core.model;

import com.islomar.payments.core.model.payment_attributes.PaymentAttributes;
import com.islomar.payments.core.model.shared.Entity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Payment implements Entity {

    private String id;
    private String type;
    private int version;
    private String organisationId;
    private PaymentAttributes attributes;

    public Payment(String id) {
        this.id = id;
    }
}
