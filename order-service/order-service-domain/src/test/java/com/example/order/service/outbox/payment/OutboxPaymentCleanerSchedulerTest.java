package com.example.order.service.outbox.payment;

import com.example.order.service.adapters.OutboxPaymentFakeAdapter;
import com.example.order.service.outbox.payment.model.OrderCreatedEventDeletedSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OutboxPaymentCleanerSchedulerTest {

    private OutboxPaymentCleanerSchedulerHandler handler;

    @BeforeEach
    void setUp() {
        OutboxPaymentFakeAdapter outboxPaymentAdapter = new OutboxPaymentFakeAdapter();
        handler = new OutboxPaymentCleanerSchedulerHandler(outboxPaymentAdapter);
    }

    @Test
    void Should_Deleted_Events() {
        //GIVEN

        //WHEN
        OrderCreatedEventDeletedSize result = handler.handle();
        //THEN
        assertAll(
                () -> assertEquals(2, result.size())
        );
    }
}
