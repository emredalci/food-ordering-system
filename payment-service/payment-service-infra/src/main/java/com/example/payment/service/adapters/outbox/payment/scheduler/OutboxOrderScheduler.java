package com.example.payment.service.adapters.outbox.payment.scheduler;

import com.example.payment.service.common.rest.BaseController;
import com.example.payment.service.outbox.order.model.OutboxOrderPublishedSize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class OutboxOrderScheduler extends BaseController {

    @Scheduled(fixedDelay = 2, timeUnit = TimeUnit.SECONDS)
    public void publishEvent() {
        OutboxOrderPublishedSize publishedSize = publish(OutboxOrderPublishedSize.class);
        log.info("{} Outbox order event are processed", publishedSize.size());
    }
}
