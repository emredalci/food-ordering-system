package com.example.payment.service.adapters.outbox.payment.scheduler;

import com.example.payment.service.common.rest.BaseController;
import com.example.payment.service.outbox.order.model.OutboxOrderDeletedSize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OutboxOrderCleanerScheduler extends BaseController {

    @Scheduled(cron = "@midnight")
    public void deleteEvent(){
        OutboxOrderDeletedSize deletedSize = publish(OutboxOrderDeletedSize.class);
        log.info("{} Outbox order events are deleted", deletedSize.size());
    }
}
