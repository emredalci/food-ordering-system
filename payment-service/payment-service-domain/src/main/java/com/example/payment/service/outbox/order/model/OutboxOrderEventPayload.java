package com.example.payment.service.outbox.order.model;

import com.example.payment.service.payment.event.PaymentEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class OutboxOrderEventPayload {
    @JsonProperty
    private String paymentId;

    @JsonProperty
    private String customerId;

    @JsonProperty
    private String orderId;

    @JsonProperty
    private BigDecimal price;

    @JsonProperty
    private LocalDateTime createdAt;

    @JsonProperty
    private String paymentStatus;

    @JsonProperty
    private List<String> failureMessages;

    public static OutboxOrderEventPayload of(PaymentEvent event) {
        return OutboxOrderEventPayload.builder()
                .paymentId(event.getPayment().getId().toString())
                .customerId(event.getPayment().getCustomerId().toString())
                .orderId(event.getPayment().getOrderId().toString())
                .price(event.getPayment().getPrice().amount())
                .paymentStatus(event.getPayment().getPaymentStatus().name())
                .createdAt(event.getCreatedAt())
                .failureMessages(event.getFailureMessages())
                .build();
    }
}
