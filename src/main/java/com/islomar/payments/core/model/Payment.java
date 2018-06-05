package com.islomar.payments.core.model;

import com.islomar.payments.core.model.payment_attributes.PaymentAttributes;
import com.islomar.payments.core.model.shared.Entity;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Payment implements Entity {

    @NotNull
    private String id;
    private PaymentType type;
    private int version;
    private String organisationId;
    private PaymentAttributes attributes;

    public Payment(String id, PaymentType type, int version, String organisationId, PaymentAttributes attributes) {
        this.id = id;
        this.type = type;
        this.version = version;
        this.organisationId = organisationId;
        this.attributes = attributes;
    }
}
