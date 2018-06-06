package com.islomar.payments.web;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.islomar.payments.core.model.PaymentType;
import com.islomar.payments.core.model.payment_attributes.PaymentAttributes;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UpsertPaymentCommand {

    @NotNull
    private PaymentType type;
    private int version;
    @NotBlank
    private String organisationId;
    @NotNull
    private PaymentAttributes attributes;
}
