package com.islomar.payments.core.model;

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
