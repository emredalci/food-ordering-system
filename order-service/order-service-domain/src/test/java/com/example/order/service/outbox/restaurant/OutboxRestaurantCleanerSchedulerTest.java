package com.example.order.service.outbox.restaurant;

import com.example.order.service.adapters.OutboxRestaurantFakeAdapter;
import com.example.order.service.outbox.restaurant.model.OrderPaidEventDeletedSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OutboxRestaurantCleanerSchedulerTest {

    private OutboxRestaurantCleanerSchedulerHandler handler;

    @BeforeEach
    void setUp() {
        OutboxRestaurantFakeAdapter outboxRestaurantAdapter = new OutboxRestaurantFakeAdapter();
        handler = new OutboxRestaurantCleanerSchedulerHandler(outboxRestaurantAdapter);
    }

    @Test
    void Should_Deleted_Events() {
        //GIVEN

        //WHEN
        OrderPaidEventDeletedSize result = handler.handle();
        //THEN
        assertAll(
                () -> assertEquals(2, result.size())
        );
    }

}
