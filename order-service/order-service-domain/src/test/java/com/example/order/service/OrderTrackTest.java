package com.example.order.service;

import com.example.order.service.adapters.OrderFakeAdapter;
import com.example.order.service.order.OrderTrackUseCaseHandler;
import com.example.order.service.order.model.Order;
import com.example.order.service.order.usecase.OrderTrackUseCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderTrackTest {

    private OrderTrackUseCaseHandler handler;

    @BeforeEach
    void setUp() {
        OrderFakeAdapter orderAdapter = new OrderFakeAdapter();
        handler = new OrderTrackUseCaseHandler(orderAdapter);
    }

    @Test
    void Should_Return_Order_When_UseCase_Is_Sufficient() {
        //GIVEN
        OrderTrackUseCase useCase = new OrderTrackUseCase(UUID.fromString("e69e8491-6aca-4a7a-b7de-c42fd949f92b"));
        //WHEN
        Order order = handler.handler(useCase);
        //THEN
        assertAll(
                () -> assertTrue(Objects.nonNull(order.getTrackingId())),
                () -> assertTrue(Objects.nonNull(order.getOrderStatus())),
                () -> assertTrue(Objects.isNull(order.getFailureMessages()) || order.getFailureMessages().isEmpty())
        );
    }
}
