package com.example.order.service.order.port;

import com.example.order.service.order.model.Order;

import java.util.UUID;

public interface OrderPort {

    void save(Order order);

    Order findById(UUID id);

    Order findByTrackingId(UUID trackingId);
}
