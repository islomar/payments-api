package com.islomar.payments.core.infrastructure;

import com.islomar.payments.core.model.Payment;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public PaymentDTO toDTO(Payment payment) {
        return modelMapper.map(payment, PaymentDTO.class);
    }

    public Payment fromDTO(PaymentDTO paymentDTO) {
        return modelMapper.map(paymentDTO, Payment.class);
    }
}
