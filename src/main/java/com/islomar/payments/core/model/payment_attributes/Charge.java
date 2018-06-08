package com.islomar.payments.core.model.payment_attributes;

import com.islomar.payments.core.model.shared.ValueObject;
import lombok.*;

import javax.validation.constraints.NotNull;
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
    @NotNull private BigDecimal amount;
    @NotNull private Currency currency;
}
