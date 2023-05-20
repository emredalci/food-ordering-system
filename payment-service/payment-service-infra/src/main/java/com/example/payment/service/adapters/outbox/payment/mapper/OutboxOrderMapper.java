package com.example.payment.service.adapters.outbox.payment.mapper;

import com.example.payment.service.adapters.outbox.payment.jpa.entity.OutboxOrderEntity;
import com.example.payment.service.outbox.order.model.OutboxOrderMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OutboxOrderMapper {

    OutboxOrderMapper INSTANCE = Mappers.getMapper(OutboxOrderMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "sagaId", target = "sagaId")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "processedAt", target = "processedAt")
    @Mapping(source = "payload", target = "payload")
    @Mapping(source = "outboxStatus", target = "outboxStatus")
    @Mapping(source = "paymentStatus", target = "paymentStatus")
    OutboxOrderEntity map(OutboxOrderMessage message);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "sagaId", target = "sagaId")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "processedAt", target = "processedAt")
    @Mapping(source = "payload", target = "payload")
    @Mapping(source = "outboxStatus", target = "outboxStatus")
    @Mapping(source = "paymentStatus", target = "paymentStatus")
    OutboxOrderMessage map(OutboxOrderEntity entity);
}
