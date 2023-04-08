package com.example.order.service.order.port;

import com.example.order.service.order.model.Order;
import com.example.order.service.order.usecase.OrderCreateUseCase;

import java.util.UUID;

public interface OrderPort {

    Order save(OrderCreateUseCase useCase);

    Order findByTrackingId(UUID trackingId);
}
