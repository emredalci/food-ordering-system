package com.example.order.service.adapters.outbox.payment.listener.model;

import com.example.order.service.common.exception.OrderServiceBusinessException;
import com.example.order.service.outbox.payment.usecase.OutboxPaymentListenerUseCase;
import com.example.order.service.payment.model.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PaymentResponse(String id,
                              String sagaId,
                              String orderId,
                              String paymentId,
                              String customerId,
                              BigDecimal price,
                              LocalDateTime createdAt,
                              String paymentStatus,
                              List<String> failureMessages) {

    public OutboxPaymentListenerUseCase toUseCase(){
        return new OutboxPaymentListenerUseCase(UUID.fromString(id), UUID.fromString(sagaId),
                UUID.fromString(orderId), UUID.fromString(paymentId), UUID.fromString(customerId), price, createdAt,
                toPaymentStatus(paymentStatus), failureMessages);
    }

    private PaymentStatus toPaymentStatus(String paymentStatus) {
        return switch (paymentStatus) {
            case "COMPLETED" -> PaymentStatus.COMPLETED;
            case "CANCELLED" -> PaymentStatus.CANCELLED;
            case "FAILED" -> PaymentStatus.FAILED;
            default -> throw new OrderServiceBusinessException("payment.status.not.valid");
        };
    }
}
