package com.islomar.payments.core.model.payment_attributes;

import com.islomar.payments.core.model.shared.ValueObject;
import lombok.*;

import java.math.BigDecimal;
import java.util.Currency;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentAttributes implements ValueObject {
    private BigDecimal amount;
    private Currency currency;
    private String endToEndReference;
    private String paymentId;
    private String paymentPurpose;
    private DebtorParty debtorParty;
    private Forex fx;
    private ChargesInformation chargesInformation;
    private SponsorParty sponsorParty;
    private BeneficiaryParty beneficiaryParty;
}
