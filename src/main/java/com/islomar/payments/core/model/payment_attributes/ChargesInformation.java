package com.islomar.payments.core.model.payment_attributes;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.islomar.payments.core.model.PaymentValidator;
import com.islomar.payments.core.model.shared.ValueObject;
import lombok.*;

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
    private String bearerCode;       //TODO create a type!
    private List<Charge> senderCharges;
    private BigDecimal receiverChargesAmount;
    private Currency receiverChargesCurrency;
}
