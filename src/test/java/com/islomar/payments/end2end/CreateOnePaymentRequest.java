package com.islomar.payments.end2end;

import com.islomar.payments.core.model.payment_attributes.PaymentAttributes;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateOnePaymentRequest {
    private String id;
    private String type;
    private String organisationId;
    private PaymentAttributes attributes;
}
