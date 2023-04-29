package com.example.order.service.adapters.outbox.restaurant.mapper;

import com.example.order.service.adapters.outbox.restaurant.jpa.entity.OutboxRestaurantEntity;
import com.example.order.service.order.event.OrderPaidEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OutboxRestaurantMapper {

    OutboxRestaurantMapper INSTANCE = Mappers.getMapper(OutboxRestaurantMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "sagaId", target = "sagaId")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "processedAt", target = "processedAt")
    @Mapping(source = "payload", target = "payload")
    @Mapping(source = "sagaStatus", target = "sagaStatus")
    @Mapping(source = "orderStatus", target = "orderStatus")
    @Mapping(source = "outboxStatus", target = "outboxStatus")
    OutboxRestaurantEntity map(OrderPaidEvent orderPaidEvent);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "sagaId", target = "sagaId")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "processedAt", target = "processedAt")
    @Mapping(source = "payload", target = "payload")
    @Mapping(source = "sagaStatus", target = "sagaStatus")
    @Mapping(source = "orderStatus", target = "orderStatus")
    @Mapping(source = "outboxStatus", target = "outboxStatus")
    OrderPaidEvent map(OutboxRestaurantEntity entity);
}
