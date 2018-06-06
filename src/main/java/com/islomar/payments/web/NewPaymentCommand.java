package com.islomar.payments.web;

import com.islomar.payments.core.model.PaymentType;
import com.islomar.payments.core.model.payment_attributes.PaymentAttributes;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewPaymentCommand {

    private PaymentType type;
    private int version;
    private String organisationId;
    private PaymentAttributes attributes;
}
