package com.example.resturant.service.adapter.outbox.order.scheduler;

import com.example.restaurant.service.outbox.order.model.OutboxOrderDeletedSize;
import com.example.resturant.service.common.rest.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OutboxOrderCleanerScheduler extends BaseController {

    @Scheduled(cron = "@midnight")
    public void publish(){
        OutboxOrderDeletedSize deletedSize = publish(OutboxOrderDeletedSize.class);
        log.info("{} Outbox order events are deleted", deletedSize.size());
    }
}
