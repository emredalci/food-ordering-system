package com.example.order.service.adapters.outbox.payment.scheduler;

import com.example.order.service.common.rest.BaseController;
import com.example.order.service.outbox.payment.model.OrderCreatedEventDeletedSize;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OutboxPaymentCleanerScheduler extends BaseController {

    @Scheduled(cron = "@midnight")
    public void deleteEvent() {
        OrderCreatedEventDeletedSize size = publish(OrderCreatedEventDeletedSize.class);
        log.info("{} event has just deleted.", size.size());
    }
}
