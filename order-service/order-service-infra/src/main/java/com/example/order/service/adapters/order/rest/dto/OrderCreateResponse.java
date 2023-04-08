package com.example.order.service.adapters.order.rest.dto;

import com.example.order.service.order.model.Order;
import com.example.order.service.order.model.OrderStatus;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderCreateResponse(@NotNull UUID orderTrackingId,
                                  @NotNull OrderStatus orderStatus) {

    public static OrderCreateResponse fromModel(Order order){
        return new OrderCreateResponse(order.getTrackingId(), order.getOrderStatus());
    }
}
