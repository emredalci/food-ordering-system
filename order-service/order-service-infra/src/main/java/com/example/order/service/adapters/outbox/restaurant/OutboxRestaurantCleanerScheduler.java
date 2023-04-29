package com.example.order.service.adapters.outbox.restaurant;

import com.example.order.service.common.rest.BaseController;
import com.example.order.service.outbox.restaurant.model.OrderPaidEventDeletedSize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OutboxRestaurantCleanerScheduler extends BaseController {

    @Scheduled(cron = "@midnight")
    public void deleteEvents() {
        OrderPaidEventDeletedSize size = publish(OrderPaidEventDeletedSize.class);
        log.info("{} OrderPaidEvent event has just deleted.", size.size());
    }

}
