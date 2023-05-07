package com.example.order.service.outbox.restaurant;

import com.example.order.service.adapters.OutboxRestaurantEventFakeAdapter;
import com.example.order.service.adapters.OutboxRestaurantFakeAdapter;
import com.example.order.service.outbox.restaurant.model.OrderPaidEventProcessedSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OutboxRestaurantSchedulerTest {

    private OutboxRestaurantSchedulerHandler handler;

    @BeforeEach
    void setUp() {
        OutboxRestaurantEventFakeAdapter outboxRestaurantEventAdapter = new OutboxRestaurantEventFakeAdapter();
        OutboxRestaurantFakeAdapter outboxRestaurantAdapter = new OutboxRestaurantFakeAdapter();
        handler = new OutboxRestaurantSchedulerHandler(outboxRestaurantAdapter,outboxRestaurantEventAdapter);
    }


    @Test
    void Should_Order_Paid_Events_Processed() {
        //GIVEN

        //WHEN
        OrderPaidEventProcessedSize result = handler.handle();
        //THEN
        assertAll(
                () -> assertEquals(2, result.size())
        );
    }
}
