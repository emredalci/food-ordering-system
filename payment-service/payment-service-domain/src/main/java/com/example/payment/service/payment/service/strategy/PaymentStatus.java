package com.example.payment.service.payment.service.strategy;

import com.example.payment.service.payment.model.PaymentOrderStatus;
import com.example.payment.service.payment.usecase.PaymentListenerUseCase;

public interface PaymentStatus {

    void execute(PaymentListenerUseCase useCase);

    PaymentOrderStatus getName();
}
