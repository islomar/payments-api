package com.islomar.payments.core.infrastructure;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.islomar.payments.core.model.entities.PaymentAttributes;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PaymentTO {
    private String id;
    private String type;
    private String organisationId;
    private PaymentAttributes attributes;
}
