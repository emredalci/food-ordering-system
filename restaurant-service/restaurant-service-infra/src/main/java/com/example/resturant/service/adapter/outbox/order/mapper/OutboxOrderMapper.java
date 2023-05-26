package com.example.resturant.service.adapter.outbox.order.mapper;


import com.example.restaurant.service.outbox.order.model.OutboxOrderMessage;
import com.example.resturant.service.adapter.outbox.order.jpa.entity.OutboxOrderEntity;
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
    @Mapping(source = "approvalStatus", target = "approvalStatus")
    OutboxOrderEntity map(OutboxOrderMessage message);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "sagaId", target = "sagaId")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "processedAt", target = "processedAt")
    @Mapping(source = "payload", target = "payload")
    @Mapping(source = "outboxStatus", target = "outboxStatus")
    @Mapping(source = "approvalStatus", target = "approvalStatus")
    OutboxOrderMessage map(OutboxOrderEntity entity);
}
