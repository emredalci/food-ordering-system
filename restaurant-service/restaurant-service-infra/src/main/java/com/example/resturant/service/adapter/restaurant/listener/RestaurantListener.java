package com.example.resturant.service.adapter.restaurant.listener;

import com.example.resturant.service.adapter.restaurant.listener.model.RestaurantRequest;
import com.example.resturant.service.common.rest.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RestaurantListener extends BaseController {

    @KafkaListener(topics = "${restaurant-service.restaurant-request-topic-name}")
    void receive(@Payload List<RestaurantRequest> messages) {
        messages.forEach(message -> publish(message.toUseCase()));
    }
}
