package com.example.payment.service.payment.usecase;

import com.example.payment.service.common.usecase.UseCase;
import com.example.payment.service.payment.model.PaymentOrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentListenerUseCase (UUID id,
                                      UUID sagaId,
                                      UUID orderId,
                                      UUID customerId,
                                      BigDecimal price,
                                      LocalDateTime createdAt,
                                      PaymentOrderStatus orderStatus) implements UseCase {



}
