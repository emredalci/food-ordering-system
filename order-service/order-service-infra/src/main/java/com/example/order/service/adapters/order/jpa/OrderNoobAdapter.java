package com.example.order.service.adapters.order.jpa;

import com.example.order.service.order.model.Order;
import com.example.order.service.order.port.OrderPort;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@ConditionalOnProperty(name = "adapters.order.noob-data-adapter", havingValue = "true", matchIfMissing = false)
public class OrderNoobAdapter implements OrderPort {
    @Override
    public void save(Order order) {

    }

    @Override
    public Order findById(UUID id) {
        return null;
    }

    @Override
    public Order findByTrackingId(UUID trackingId) {
        return null;
    }
}
