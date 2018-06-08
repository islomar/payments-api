package com.islomar.payments.core.model.payment_attributes;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.islomar.payments.core.model.shared.ValueObject;
import lombok.*;

import javax.validation.constraints.NotBlank;
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
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Forex implements ValueObject, Serializable {
    @NotBlank private String contractReference;
    @NotNull private BigDecimal exchangeRate;
    @NotNull private BigDecimal originalAmount;
    @NotNull private Currency originalCurrency;
}
