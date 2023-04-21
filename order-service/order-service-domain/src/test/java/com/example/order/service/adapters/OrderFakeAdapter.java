package com.example.order.service.adapters;

import com.example.order.service.order.model.Order;
import com.example.order.service.order.model.OrderStatus;
import com.example.order.service.order.port.OrderPort;

import java.util.UUID;

public class OrderFakeAdapter implements OrderPort {

    @Override
    public void save(Order order) {

    }

    @Override
    public Order findByTrackingId(UUID trackingId) {

        return Order.builder()
                .trackingId(trackingId)
                .orderStatus(OrderStatus.PENDING)
                .build();
    }
}
