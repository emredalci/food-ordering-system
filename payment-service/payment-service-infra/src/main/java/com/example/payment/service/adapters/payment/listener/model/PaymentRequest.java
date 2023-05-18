package com.example.payment.service.adapters.payment.listener.model;

import com.example.payment.service.common.exception.PaymentServiceBusinessException;
import com.example.payment.service.payment.model.PaymentOrderStatus;
import com.example.payment.service.payment.usecase.PaymentListenerUseCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class PaymentRequest {
    private String id;
    private String sagaId;
    private String orderId;
    private String customerId;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private String paymentOrderStatus;

    public void setPaymentOrderStatus(String paymentOrderStatus) {
        this.paymentOrderStatus = paymentOrderStatus;
    }

    public PaymentListenerUseCase toUseCase() {
        return new PaymentListenerUseCase(UUID.fromString(id),
                UUID.fromString(sagaId),
                UUID.fromString(orderId),
                UUID.fromString(orderId),
                price,
                createdAt,
                toPaymentOrderStatus(paymentOrderStatus));
    }

    private PaymentOrderStatus toPaymentOrderStatus(String paymentOrderStatus) {
        return switch (paymentOrderStatus) {
            case "PENDING" -> PaymentOrderStatus.PENDING;
            case "CANCELLED" -> PaymentOrderStatus.CANCELLED;
            default -> throw new PaymentServiceBusinessException("payment.order.status.enum.not.valid");
        };
    }
}
