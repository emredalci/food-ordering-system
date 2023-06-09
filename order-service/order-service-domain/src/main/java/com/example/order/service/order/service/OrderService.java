package com.example.order.service.order.service;

import com.example.order.service.common.DomainComponent;
import com.example.order.service.common.exception.OrderServiceBusinessException;
import com.example.order.service.order.event.OrderCancelledEvent;
import com.example.order.service.order.event.OrderCreatedEvent;
import com.example.order.service.order.event.OrderPaidEvent;
import com.example.order.service.order.model.Order;
import com.example.order.service.order.model.OrderStatus;
import com.example.order.service.outbox.OutboxStatus;
import com.example.order.service.outbox.payment.model.PaymentPayload;
import com.example.order.service.outbox.restaurant.model.RestaurantPayload;
import com.example.order.service.product.model.Product;
import com.example.order.service.restaurant.model.Restaurant;
import com.example.order.service.saga.SagaStatus;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@DomainComponent
public class OrderService {

    public OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant) {
        validateRestaurant(restaurant);
        setOrderProductInformation(order, restaurant);
        order.validateOrder();
        order.initializeOrder();
        log.info("Order with id: {} is initiated", order.getId());
        return buildOrderCreatedEvent(order);
    }

    public OrderPaidEvent payOrder(Order order, UUID sagaId) {
        order.pay();
        log.info("Order with id: {} is paid", order.getId());
        SagaStatus sagaStatus = toSagaStatus((order.getOrderStatus()));
        return buildOrderPaidEvent(order, sagaId, sagaStatus);
    }

    public void approveOrder(Order order) {
        order.approve();
        log.info("Order with id: {} is approved", order.getId());
    }

    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages) {
        order.initCancel(failureMessages);
        log.info("Order payment is cancelling for order id: {}", order.getId());
        return new OrderCancelledEvent(order.getOrderStatus(), LocalDateTime.now());
    }

    public void cancelOrder(Order order, List<String> failureMessages) {
        order.cancel(failureMessages);
        log.info("Order with id: {} is cancelled", order.getId());
    }

    private void validateRestaurant(Restaurant restaurant) {
        if (Boolean.FALSE.equals(restaurant.active())){
            throw new OrderServiceBusinessException("restaurant.not.active");
        }
    }

    private void setOrderProductInformation(Order order, Restaurant restaurant) {
        Map<UUID, Product> restaurantProductMap = restaurant.products()
                .stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        order.getItems().forEach(orderItem -> {
            if (restaurantProductMap.containsKey(orderItem.getProduct().getId())){
                Product restaurantProduct = restaurantProductMap.get(orderItem.getProduct().getId());
                orderItem.getProduct().updateWithConfirmedNameAndPrice(restaurantProduct.getName(), restaurantProduct.getPrice());
            }
        });
    }

    public SagaStatus toSagaStatus(OrderStatus orderStatus) {
        return switch (orderStatus) {
            case PENDING -> SagaStatus.STARTED;
            case PAID -> SagaStatus.PROCESSING;
            case APPROVED -> SagaStatus.SUCCEEDED;
            case CANCELLING -> SagaStatus.COMPENSATING;
            case CANCELLED -> SagaStatus.COMPENSATED;
        };
    }

    private OrderCreatedEvent buildOrderCreatedEvent(Order order) {
        return OrderCreatedEvent.builder()
                .id(UUID.randomUUID())
                .sagaId(UUID.randomUUID())
                .createdAt(LocalDateTime.now())
                .payload(PaymentPayload.fromOrder(order).toString())
                .orderStatus(order.getOrderStatus())
                .sagaStatus(toSagaStatus(order.getOrderStatus()))
                .outboxStatus(OutboxStatus.STARTED)
                .build();
    }

    private OrderPaidEvent buildOrderPaidEvent(Order order, UUID sagaId, SagaStatus sagaStatus){
        return OrderPaidEvent.builder()
                .id(UUID.randomUUID())
                .sagaId(sagaId)
                .createdAt(LocalDateTime.now())
                .payload(RestaurantPayload.fromOrder(order).toString())
                .orderStatus(order.getOrderStatus())
                .sagaStatus(sagaStatus)
                .outboxStatus(OutboxStatus.STARTED)
                .build();
    }
}
