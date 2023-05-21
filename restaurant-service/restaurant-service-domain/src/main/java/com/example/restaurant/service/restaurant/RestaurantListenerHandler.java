package com.example.restaurant.service.restaurant;

import com.example.restaurant.service.common.DomainComponent;
import com.example.restaurant.service.common.exception.RestaurantServiceBusinessException;
import com.example.restaurant.service.common.usecase.RegisterHelper;
import com.example.restaurant.service.common.usecase.VoidUseCaseHandler;
import com.example.restaurant.service.outbox.order.model.OutboxOrderEventPayload;
import com.example.restaurant.service.outbox.order.model.OutboxOrderMessage;
import com.example.restaurant.service.outbox.order.port.OutboxOrderPort;
import com.example.restaurant.service.product.Product;
import com.example.restaurant.service.restaurant.event.RestaurantEvent;
import com.example.restaurant.service.restaurant.model.Restaurant;
import com.example.restaurant.service.restaurant.port.RestaurantPort;
import com.example.restaurant.service.restaurant.service.RestaurantService;
import com.example.restaurant.service.restaurant.usecase.RestaurantListenerUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@DomainComponent
@Slf4j
public class RestaurantListenerHandler extends RegisterHelper implements VoidUseCaseHandler<RestaurantListenerUseCase> {

    private final RestaurantPort restaurantPort;
    private final RestaurantService restaurantService;
    private final OutboxOrderPort outboxOrderPort;
    private final ObjectMapper objectMapper;

    public RestaurantListenerHandler(RestaurantPort restaurantPort, RestaurantService restaurantService, OutboxOrderPort outboxOrderPort, ObjectMapper objectMapper) {
        this.restaurantPort = restaurantPort;
        this.restaurantService = restaurantService;
        this.outboxOrderPort = outboxOrderPort;
        this.objectMapper = objectMapper;
        register(RestaurantListenerUseCase.class, this);
    }

    @Override
    @Transactional
    public void handle(RestaurantListenerUseCase useCase) {
        //TODO publishIfOutboxMessageProcessed
        List<UUID> productIds = useCase.products().stream().map(Product::getId).toList();
        Restaurant restaurant = restaurantPort.getByIdAndProductIds(useCase.restaurantId(), productIds);
        RestaurantEvent event = restaurantService.validateOrder(restaurant);
        restaurantPort.save(restaurant);
        OutboxOrderMessage message = OutboxOrderMessage.of(event, createPayload(event), useCase.sagaId());
        outboxOrderPort.save(message);
    }

    private String createPayload(RestaurantEvent event) {
        OutboxOrderEventPayload payload = OutboxOrderEventPayload.of(event);
        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RestaurantServiceBusinessException("payload.not.created");
        }
    }
}
