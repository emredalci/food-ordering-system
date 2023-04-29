package com.example.order.service.outbox.restaurant.model;

import com.example.order.service.order.model.Order;
import com.example.order.service.order.model.OrderItem;
import com.example.order.service.restaurant.model.RestaurantOrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record RestaurantPayload(@JsonProperty String orderId,
                                @JsonProperty String restaurantId,
                                @JsonProperty BigDecimal price,
                                @JsonProperty LocalDateTime createdAt,
                                @JsonProperty String restaurantOrderStatus,
                                @JsonProperty List<RestaurantProductPayload> products) {

    public static RestaurantPayload fromOrder(Order order) {
        return new RestaurantPayload(order.getId().toString(),
                order.getRestaurantId().toString(),
                order.getPrice().amount(),
                LocalDateTime.now(),
                RestaurantOrderStatus.PAID.toString(),
                fromOrderItems(order.getItems()));
    }

    private static List<RestaurantProductPayload> fromOrderItems(List<OrderItem> orderItems) {
        return orderItems.stream().map(orderItem -> new RestaurantProductPayload(orderItem.getProduct().getId().toString(), orderItem.getQuantity())).toList();
    }


}
