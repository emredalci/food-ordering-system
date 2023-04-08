package com.example.order.service.order.usecase;

import com.example.order.service.common.usecase.UseCase;

import java.util.UUID;


public record OrderTrackUseCase(UUID orderTrackingId) implements UseCase {

    public static OrderTrackUseCase from(UUID orderTrackingId){
        return new OrderTrackUseCase(orderTrackingId);
    }
}
