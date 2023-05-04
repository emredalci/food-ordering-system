package com.example.order.service.adapters.outbox.restaurant.listener;

import com.example.order.service.adapters.outbox.restaurant.listener.model.RestaurantResponse;
import com.example.order.service.common.rest.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OutboxRestaurantListener extends BaseController {

    @KafkaListener(topics = "${restaurant-response-topic-name}")
    void receive(@Payload List<RestaurantResponse> messages) {
        messages.forEach(message -> {
            try {
                publish(message.toUseCase());
            } catch (OptimisticLockingFailureException e) {
                log.info("Caught optimistic locking exception, order id: {}", message.orderId());
            }
        });
    }
}
