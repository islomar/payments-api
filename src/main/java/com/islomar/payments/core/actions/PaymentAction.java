package com.islomar.payments.core.actions;

import com.islomar.payments.core.infrastructure.PaymentTO;
import com.islomar.payments.core.model.Payment;
import org.modelmapper.ModelMapper;

public abstract class PaymentAction {

    private ModelMapper modelMapper = new ModelMapper();

    public PaymentTO toDTO(Payment payment) {
        return modelMapper.map(payment, PaymentTO.class);
    }

    public Payment fromDTO(PaymentTO paymentDTO) {
        return modelMapper.map(paymentDTO, Payment.class);
    }

}
