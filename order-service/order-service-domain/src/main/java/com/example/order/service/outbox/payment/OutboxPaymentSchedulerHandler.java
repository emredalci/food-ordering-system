package com.example.order.service.outbox.payment;

import com.example.order.service.common.DomainComponent;
import com.example.order.service.common.usecase.NoUseCaseHandler;
import com.example.order.service.common.usecase.RegisterHelper;
import com.example.order.service.order.event.OrderCreatedEvent;
import com.example.order.service.outbox.OutboxStatus;
import com.example.order.service.outbox.payment.model.OrderCreatedEventProcessedSize;
import com.example.order.service.outbox.payment.port.OutboxPaymentEventPort;
import com.example.order.service.outbox.payment.port.OutboxPaymentPort;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@DomainComponent
public class OutboxPaymentSchedulerHandler extends RegisterHelper implements NoUseCaseHandler<OrderCreatedEventProcessedSize> {

    private final OutboxPaymentPort outboxPaymentPort;

    private final OutboxPaymentEventPort outboxPaymentEventPort;

    public OutboxPaymentSchedulerHandler(OutboxPaymentPort outboxPaymentPort, OutboxPaymentEventPort outboxPaymentEventPort) {
        this.outboxPaymentPort = outboxPaymentPort;
        this.outboxPaymentEventPort = outboxPaymentEventPort;
        register(OrderCreatedEventProcessedSize.class, this);
    }

    @Override
    public OrderCreatedEventProcessedSize handle() {
        List<OrderCreatedEvent> events = outboxPaymentPort.getPublishReadyEvents();
        if (Boolean.TRUE.equals(events.isEmpty())) {
            return new OrderCreatedEventProcessedSize(0);
        }
        events.forEach(event -> outboxPaymentEventPort.publish(event, this::updateOutboxStatus));
        return new OrderCreatedEventProcessedSize(events.size());
    }

    private void updateOutboxStatus(OrderCreatedEvent event, OutboxStatus status) {
        event.setOutboxStatus(status);
        outboxPaymentPort.save(event);
    }


}
