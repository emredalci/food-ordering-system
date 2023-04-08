package com.example.order.service.order.service;

import com.example.order.service.common.DomainComponent;
import com.example.order.service.common.exception.OrderServiceBusinessException;
import com.example.order.service.order.event.OrderCancelledEvent;
import com.example.order.service.order.event.OrderCreatedEvent;
import com.example.order.service.order.event.OrderPaidEvent;
import com.example.order.service.order.model.Order;
import com.example.order.service.product.model.Product;
import com.example.order.service.restaurant.model.Restaurant;
import lombok.extern.slf4j.Slf4j;

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
        return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of("UTC")));
    }

    public OrderPaidEvent payOrder(Order order) {
        order.pay();
        log.info("Order with id: {} is paid", order.getId());
        return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of("UTC")));
    }

    public void approveOrder(Order order) {
        order.approve();
        log.info("Order with id: {} is approved", order.getId());
    }

    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages) {
        order.initCancel(failureMessages);
        log.info("Order payment is cancelling for order id: {}", order.getId());
        return new OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of("UTC")));
    }

    public void cancelOrder(Order order, List<String> failureMessages) {
        order.cancel(failureMessages);
        log.info("Order with id: {} is cancelled", order.getId());
    }

    private void validateRestaurant(Restaurant restaurant) {
        if (Boolean.FALSE.equals(restaurant.isActive())){
            throw new OrderServiceBusinessException("restaurant.not.active");
        }
    }

    private void setOrderProductInformation(Order order, Restaurant restaurant) {
        Map<UUID, Product> restaurantProductMap = restaurant.getProducts()
                .stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        order.getItems().forEach(orderItem -> {
            if (restaurantProductMap.containsKey(orderItem.getProduct().getId())){
                Product restaurantProduct = restaurantProductMap.get(orderItem.getProduct().getId());
                orderItem.getProduct().updateWithConfirmedNameAndPrice(restaurantProduct.getName(), restaurantProduct.getPrice());
            }
        });
    }
}
