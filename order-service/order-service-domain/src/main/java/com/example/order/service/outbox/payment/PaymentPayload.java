package com.example.order.service.outbox.payment;

import com.example.order.service.order.model.Order;
import com.example.order.service.payment.model.PaymentOrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentPayload(@JsonProperty String orderId,
                             @JsonProperty String customerId,
                             @JsonProperty BigDecimal price,
                             @JsonProperty LocalDateTime createdAt,
                             @JsonProperty String paymentOrderStatus) {

    public static PaymentPayload fromOrder(Order order) {
        return new PaymentPayload(order.getId().toString(),
                order.getCustomerId().toString(),
                order.getPrice().amount(),
                LocalDateTime.now(),
                PaymentOrderStatus.PENDING.name());

    }
}
