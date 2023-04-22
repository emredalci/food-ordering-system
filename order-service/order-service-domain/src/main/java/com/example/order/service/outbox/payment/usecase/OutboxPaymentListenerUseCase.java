package com.example.order.service.outbox.payment.usecase;

import com.example.order.service.common.usecase.UseCase;
import com.example.order.service.payment.model.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OutboxPaymentListenerUseCase(UUID id,
                                           UUID sagaId,
                                           UUID orderId,
                                           UUID paymentId,
                                           UUID customerId,
                                           BigDecimal price,
                                           LocalDateTime createdAt,
                                           PaymentStatus paymentStatus,
                                           List<String> failureMessages) implements UseCase {

}
