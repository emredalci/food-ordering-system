package com.example.order.service.adapters.payment.event.message;

import com.example.order.service.payment.model.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record PaymentResponse(String id,
                              String sagaId,
                              String orderId,
                              String paymentId,
                              String customerId,
                              BigDecimal price,
                              Instant createdAt,
                              PaymentStatus paymentStatus,
                              List<String> failureMessages) {
}
