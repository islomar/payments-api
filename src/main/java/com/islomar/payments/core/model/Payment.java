package com.islomar.payments.core.model;

import com.islomar.payments.core.model.payment_attributes.PaymentAttributes;
import com.islomar.payments.core.model.shared.Entity;
import com.islomar.payments.core.model.shared.Validable;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;


@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Payment implements Entity, Validable, Serializable {

    @NotBlank private String id;
    @NotNull private PaymentType type;
    @PositiveOrZero private int version;
    @NotBlank private String organisationId;
    @NotNull @Valid private PaymentAttributes attributes;

    public Payment(String id, PaymentType type, int version, String organisationId, PaymentAttributes attributes) {
        this.id = id;
        this.type = type;
        this.version = version;
        this.organisationId = organisationId;
        this.attributes = attributes;
    }

    @Override
    public void validate(PaymentValidator validator) {
        validator.validate(this);
    }
}
