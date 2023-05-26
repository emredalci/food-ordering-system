package com.example.resturant.service.adapter.outbox.order.scheduler;

import com.example.restaurant.service.outbox.order.model.OutboxOrderPublishedSize;
import com.example.resturant.service.common.rest.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class OutboxOrderScheduler extends BaseController {

    @Scheduled(fixedDelay = 3, timeUnit = TimeUnit.SECONDS)
    public void publishEvent(){
        OutboxOrderPublishedSize publishedSize = publish(OutboxOrderPublishedSize.class);
        log.info("{} Outbox order event are processed", publishedSize.size());
    }
}
