package com.example.order.service.adapters.order.rest.dto;

import com.example.order.service.order.model.Order;
import com.example.order.service.order.model.OrderStatus;

import java.util.List;
import java.util.UUID;

public record OrderTrackResponse(UUID orderTrackingId,
                                 OrderStatus orderStatus,
                                 List<String> failureMessages) {

    public static OrderTrackResponse fromModel(Order order){
        return new OrderTrackResponse(order.getTrackingId(), order.getOrderStatus(), order.getFailureMessages());
    }
}
