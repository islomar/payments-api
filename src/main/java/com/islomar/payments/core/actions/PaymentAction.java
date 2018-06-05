package com.islomar.payments.core.actions;

import com.islomar.payments.core.infrastructure.PaymentDTO;
import com.islomar.payments.core.model.Payment;
import org.modelmapper.ModelMapper;

public abstract class PaymentAction {

    private ModelMapper modelMapper = new ModelMapper();

    public PaymentDTO toDTO(Payment payment) {
        return modelMapper.map(payment, PaymentDTO.class);
    }

    public Payment fromDTO(PaymentDTO paymentDTO) {
        return modelMapper.map(paymentDTO, Payment.class);
    }

}
