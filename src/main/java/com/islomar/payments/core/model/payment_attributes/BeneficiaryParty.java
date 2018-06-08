package com.islomar.payments.core.model.payment_attributes;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.islomar.payments.core.model.shared.ValueObject;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BeneficiaryParty implements ValueObject, Serializable {
    @NotBlank private String accountName;
    @NotBlank private String accountNumber;       //TODO create a type!
    @NotBlank private String accountNumberCode;   //TODO create a type!
    @NotNull private int accountType;            //TODO create a type!
    @NotBlank private String address;
    @NotBlank private String bankId;              //TODO create a type!
    @NotBlank private String bankIdCode;          //TODO create a type!
    @NotBlank private String name;
}
