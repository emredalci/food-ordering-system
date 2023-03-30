package com.example.order.service.order.event;

import com.example.order.service.common.event.DomainEvent;
import com.example.order.service.order.model.Order;

import java.time.ZonedDateTime;

public record OrderCreatedEvent(Order order, ZonedDateTime createdAt) implements DomainEvent<Order> {
}