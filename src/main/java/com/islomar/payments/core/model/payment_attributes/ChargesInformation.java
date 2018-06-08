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
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ChargesInformation implements ValueObject, Serializable {
    @NotBlank private String bearerCode;       //TODO create a type!
    @NotNull private List<Charge> senderCharges;
    @NotNull private BigDecimal receiverChargesAmount;
    @NotNull private Currency receiverChargesCurrency;
}
