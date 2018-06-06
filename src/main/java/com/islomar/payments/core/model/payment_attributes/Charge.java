package com.islomar.payments.core.model.payment_attributes;

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
public class Charge implements ValueObject, Serializable {
    private BigDecimal amount;
    private Currency currency;
}
