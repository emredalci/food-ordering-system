package com.example.order.service.adapters.outbox.payment.mapper;

import com.example.order.service.adapters.outbox.payment.jpa.entity.PaymentOutboxEntity;
import com.example.order.service.order.event.OrderCreatedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentOutboxMapper {

    PaymentOutboxMapper INSTANCE = Mappers.getMapper(PaymentOutboxMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "sagaId", target = "sagaId")
    @Mapping(source = "sagaId", target = "sagaId")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "payload", target = "payload")
    @Mapping(source = "sagaStatus", target = "sagaStatus")
    @Mapping(source = "orderStatus", target = "orderStatus")
    @Mapping(source = "outboxStatus", target = "outboxStatus")
    PaymentOutboxEntity map(OrderCreatedEvent orderCreatedEvent);
}
