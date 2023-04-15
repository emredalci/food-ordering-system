package com.example.order.service.adapters.outbox.payment.scheduler;

import com.example.order.service.common.rest.BaseController;
import com.example.order.service.outbox.payment.model.OrderCreatedEventProcessedSize;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OutboxPaymentScheduler extends BaseController {

    @Scheduled(fixedDelay = 2, timeUnit = TimeUnit.SECONDS)
    public void publishEvent() {
        OrderCreatedEventProcessedSize eventSize = publish(OrderCreatedEventProcessedSize.class);
        log.info("{} OrderCreatedEvent is processed.", eventSize.size());
    }
}
