package com.example.order.service.outbox.payment;

import com.example.order.service.common.DomainComponent;
import com.example.order.service.common.usecase.NoUseCaseHandler;
import com.example.order.service.common.usecase.RegisterHelper;
import com.example.order.service.outbox.payment.model.OrderCreatedEventDeletedSize;
import com.example.order.service.outbox.payment.port.OutboxPaymentPort;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DomainComponent
public class OutboxPaymentCleanerSchedulerHandler extends RegisterHelper implements NoUseCaseHandler<OrderCreatedEventDeletedSize> {

    private final OutboxPaymentPort outboxPaymentPort;

    public OutboxPaymentCleanerSchedulerHandler(OutboxPaymentPort outboxPaymentPort) {
        this.outboxPaymentPort = outboxPaymentPort;
        register(OrderCreatedEventDeletedSize.class, this);
    }

    @Override
    @Transactional
    public OrderCreatedEventDeletedSize handle() {
        List<UUID> deleteReadyEventIds = outboxPaymentPort.getDeleteReadyEventIds();
        if (deleteReadyEventIds.isEmpty()){
            return new OrderCreatedEventDeletedSize(0);
        }
        outboxPaymentPort.deleteByIdList(deleteReadyEventIds);
        return new OrderCreatedEventDeletedSize(deleteReadyEventIds.size());
    }
}
