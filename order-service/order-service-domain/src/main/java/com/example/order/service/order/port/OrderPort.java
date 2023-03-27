package com.example.order.service.order.port;

import com.example.order.service.order.event.OrderCancelledEvent;
import com.example.order.service.order.event.OrderCreatedEvent;
import com.example.order.service.order.event.OrderPaidEvent;
import com.example.order.service.order.model.Order;
import com.example.order.service.restaurant.model.Restaurant;

import java.util.List;

public interface OrderPort {
    OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant);

    OrderPaidEvent payOrder(Order order);

    void approveOrder(Order order);

    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);

    void cancelOrder(Order order, List<String> failureMessages);
}
