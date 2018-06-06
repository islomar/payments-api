package com.islomar.payments.core.model.payment_attributes;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.islomar.payments.core.model.shared.ValueObject;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Forex implements ValueObject, Serializable {
    private String contractReference;
    private BigDecimal exchangeRate;
    private BigDecimal originalAmount;
    private Currency originalCurrency;
}
