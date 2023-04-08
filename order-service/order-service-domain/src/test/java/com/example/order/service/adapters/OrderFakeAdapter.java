package com.example.order.service.adapters;

import com.example.order.service.order.model.Order;
import com.example.order.service.order.port.OrderPort;
import com.example.order.service.order.usecase.OrderCreateUseCase;

import java.util.UUID;

public class OrderFakeAdapter implements OrderPort {
    @Override
    public Order save(OrderCreateUseCase useCase) {
        return null;
    }

    @Override
    public Order findByTrackingId(UUID trackingId) {
        return null;
    }
}
