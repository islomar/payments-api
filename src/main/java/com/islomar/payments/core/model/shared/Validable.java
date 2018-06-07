package com.islomar.payments.core.model.shared;

import com.islomar.payments.core.model.PaymentValidator;

public interface Validable {
    void validate(PaymentValidator validator);
}
