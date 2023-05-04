package com.example.order.service.order.event;

import com.example.order.service.common.event.DomainEvent;
import com.example.order.service.order.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public final class OrderCancelledEvent implements DomainEvent {
    private final OrderStatus orderStatus;
    private final LocalDateTime createAt;


}
