package com.example.order.service.outbox.restaurant;

import com.example.order.service.common.DomainComponent;
import com.example.order.service.common.usecase.NoUseCaseHandler;
import com.example.order.service.common.usecase.RegisterHelper;
import com.example.order.service.outbox.restaurant.model.OrderPaidEventDeletedSize;
import com.example.order.service.outbox.restaurant.port.OutboxRestaurantPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@DomainComponent
@Slf4j
public class OutboxRestaurantCleanerSchedulerHandler extends RegisterHelper implements NoUseCaseHandler<OrderPaidEventDeletedSize> {

    private final OutboxRestaurantPort outboxRestaurantPort;

    public OutboxRestaurantCleanerSchedulerHandler(OutboxRestaurantPort outboxRestaurantPort) {
        this.outboxRestaurantPort = outboxRestaurantPort;
        register(OrderPaidEventDeletedSize.class, this);
    }

    @Override
    @Transactional
    public OrderPaidEventDeletedSize handle() {
        List<UUID> ids = outboxRestaurantPort.getDeleteReadyEventIds();
        if (ids.isEmpty()) {
            return new OrderPaidEventDeletedSize(0);
        }
        outboxRestaurantPort.deleteByIdList(ids);
        return new OrderPaidEventDeletedSize(ids.size());
    }
}
