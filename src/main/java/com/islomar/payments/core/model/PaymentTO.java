package com.islomar.payments.core.model;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTO {
    private String id;
    private String type;
    private String organisationId;
    private Map<String, Object> attributes;
}
