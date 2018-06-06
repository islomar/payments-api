package com.islomar.payments.core.model.payment_attributes;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.islomar.payments.core.model.shared.ValueObject;
import lombok.*;

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
    private BigDecimal amount;
    private BeneficiaryParty beneficiaryParty;
    private ChargesInformation chargesInformation;
    private Currency currency;
    private DebtorParty debtorParty;
    private String endToEndReference;
    private Forex fx;
    private String numericReference;
    private String paymentId;               //TODO create a type!
    private String paymentPurpose;
    private String paymentScheme;           //TODO create a type!
    private String paymentType;
    private String processingDate;          //TODO use a Date with specific format
    private String reference;
    private String schemePaymentSubType;    //TODO create a type!
    private String schemePaymentType;       //TODO create a type!
    private SponsorParty sponsorParty;
}
