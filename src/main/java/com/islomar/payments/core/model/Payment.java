package com.islomar.payments.core.model;

import com.islomar.payments.core.model.payment_attributes.PaymentAttributes;
import com.islomar.payments.core.model.shared.Entity;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Payment implements Entity {

    private String id;
    private String type;
    private int version;
    private String organisationId;
    private PaymentAttributes attributes;

    public Payment(String id, String type, int version, String organisationId, PaymentAttributes attributes) {
        this.id = id;
        this.type = type;
        this.version = version;
        this.organisationId = organisationId;
        this.attributes = attributes;
    }
}
