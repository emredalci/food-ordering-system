package com.example.restaurant.service.outbox.order;

import com.example.restaurant.service.common.DomainComponent;
import com.example.restaurant.service.common.usecase.NoUseCaseHandler;
import com.example.restaurant.service.common.usecase.RegisterHelper;
import com.example.restaurant.service.outbox.OutboxStatus;
import com.example.restaurant.service.outbox.order.model.OutboxOrderMessage;
import com.example.restaurant.service.outbox.order.model.OutboxOrderPublishedSize;
import com.example.restaurant.service.outbox.order.port.OutboxOrderEventPort;
import com.example.restaurant.service.outbox.order.port.OutboxOrderPort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DomainComponent
public class OutboxOrderSchedulerHandler extends RegisterHelper implements NoUseCaseHandler<OutboxOrderPublishedSize> {

    private final OutboxOrderPort outboxOrderPort;
    private final OutboxOrderEventPort outboxOrderEventPort;

    public OutboxOrderSchedulerHandler(OutboxOrderPort outboxOrderPort, OutboxOrderEventPort outboxOrderEventPort) {
        this.outboxOrderPort = outboxOrderPort;
        this.outboxOrderEventPort = outboxOrderEventPort;
        register(OutboxOrderPublishedSize.class, this);
    }

    @Override
    @Transactional
    public OutboxOrderPublishedSize handle() {
        List<OutboxOrderMessage> messages = outboxOrderPort.getByOutboxStatus(OutboxStatus.STARTED);
        if (Boolean.TRUE.equals(messages.isEmpty())) {
            return new OutboxOrderPublishedSize(0);
        }
        messages.forEach(message -> outboxOrderEventPort.publish(message, this::updateOutboxOrderMessage));
        return new OutboxOrderPublishedSize(messages.size());
    }

    @Transactional
    public void updateOutboxOrderMessage(OutboxOrderMessage message, OutboxStatus status) {
        message.setOutboxStatus(status);
        outboxOrderPort.save(message);
    }
}
