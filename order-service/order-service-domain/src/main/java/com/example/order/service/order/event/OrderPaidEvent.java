package com.example.order.service.order.event;

import com.example.order.service.common.event.DomainEvent;
import com.example.order.service.order.model.Order;

import java.time.LocalDateTime;

public record OrderPaidEvent(Order order, LocalDateTime createAt) implements DomainEvent {
}
