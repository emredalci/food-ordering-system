package com.example.order.service.adapters.outbox.restaurant;

import com.example.order.service.common.rest.BaseController;
import com.example.order.service.outbox.restaurant.model.OrderPaidEventProcessedSize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class OutboxRestaurantScheduler extends BaseController {

    @Scheduled(fixedDelay = 2, timeUnit = TimeUnit.SECONDS)
    public void publishEvent() {
        OrderPaidEventProcessedSize eventSize = publish(OrderPaidEventProcessedSize.class);
        log.info("{} OrderPaidEvent is processed.", eventSize.size());
    }
}
