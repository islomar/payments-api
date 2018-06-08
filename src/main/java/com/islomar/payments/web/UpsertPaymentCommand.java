package com.islomar.payments.web;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.islomar.payments.core.model.PaymentType;
import com.islomar.payments.core.model.payment_attributes.PaymentAttributes;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UpsertPaymentCommand implements Serializable {

    @NotNull private PaymentType type;
    @PositiveOrZero private int version;
    @NotBlank private String organisationId;
    @NotNull @Valid private PaymentAttributes attributes;
}
