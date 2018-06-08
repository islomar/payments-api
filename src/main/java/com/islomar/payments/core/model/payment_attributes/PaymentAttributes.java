package com.islomar.payments.core.model.payment_attributes;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.islomar.payments.core.model.shared.ValueObject;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PaymentAttributes implements ValueObject, Serializable {
    @NotNull private BigDecimal amount;
    @NotNull @Valid private BeneficiaryParty beneficiaryParty;
    @NotNull @Valid private ChargesInformation chargesInformation;
    @NotNull private Currency currency;
    @NotNull @Valid private DebtorParty debtorParty;
    @NotBlank private String endToEndReference;
    @NotNull @Valid private Forex fx;
    @NotBlank private String numericReference;
    @NotBlank private String paymentId;               //TODO create a type!
    @NotBlank private String paymentPurpose;
    @NotBlank private String paymentScheme;           //TODO create a type!
    @NotBlank private String paymentType;
    @NotBlank private String processingDate;          //TODO use a Date with specific format
    @NotBlank private String reference;
    @NotBlank private String schemePaymentSubType;    //TODO create a type!
    @NotBlank private String schemePaymentType;       //TODO create a type!
    @NotNull @Valid private SponsorParty sponsorParty;
}
