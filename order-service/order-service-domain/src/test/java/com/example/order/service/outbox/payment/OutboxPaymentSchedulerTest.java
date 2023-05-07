package com.example.order.service.outbox.payment;

import com.example.order.service.adapters.OutboxPaymentFakeAdapter;
import com.example.order.service.adapters.OutboxPaymentFakeEventAdapter;
import com.example.order.service.outbox.payment.model.OrderCreatedEventProcessedSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OutboxPaymentSchedulerTest {

    private OutboxPaymentSchedulerHandler handler;

    @BeforeEach
    void setUp() {
        OutboxPaymentFakeAdapter outboxPaymentAdapter = new OutboxPaymentFakeAdapter();
        OutboxPaymentFakeEventAdapter outboxPaymentEventAdapter = new OutboxPaymentFakeEventAdapter();
        handler = new OutboxPaymentSchedulerHandler(outboxPaymentAdapter, outboxPaymentEventAdapter);
    }

    @Test
    void Should_Order_Created_Event_Processed() {
        //GIVEN

        //WHEN
        OrderCreatedEventProcessedSize result = handler.handle();
        //THEN
        assertAll(
                () -> assertEquals(2, result.size())
        );
    }
}
