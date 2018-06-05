package com.islomar.payments.core.infrastructure;

import com.islomar.payments.core.model.entities.PaymentAttributes;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTO {
    private String id;
    private String type;
    private String organisationId;
    private PaymentAttributes attributes;
}
