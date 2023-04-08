package com.example.order.service.adapters.order.jpa;

import com.example.order.service.order.model.Order;
import com.example.order.service.order.port.OrderPort;
import com.example.order.service.order.usecase.OrderCreateUseCase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@ConditionalOnProperty(name = "adapters.order.noob-data-adapter", havingValue = "true", matchIfMissing = false)
public class OrderNoobAdapter implements OrderPort {
    @Override
    public Order save(OrderCreateUseCase useCase) {
        return useCase.toOrder();
    }

    @Override
    public Order findByTrackingId(UUID trackingId) {
        return null;
    }
}
