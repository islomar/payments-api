package com.islomar.payments.core.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Currency;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAttributes {
    private BigDecimal amount;
    private Currency currency;
    private String endToEndReference;
    private String paymentId;
    private String paymentPurpose;

}
