package com.islomar.payments.core.model.payment_attributes;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.islomar.payments.core.model.PaymentValidator;
import com.islomar.payments.core.model.shared.ValueObject;
import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DebtorParty implements ValueObject, Serializable {
    private String accountName;
    private String accountNumber;       //TODO create a type!
    private String accountNumberCode;   //TODO create a type!
    private String address;
    private String bankId;              //TODO create a type!
    private String bankIdCode;          //TODO create a type!
    private String name;
}
