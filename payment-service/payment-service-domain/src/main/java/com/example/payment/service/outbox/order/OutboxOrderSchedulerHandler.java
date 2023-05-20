package com.example.payment.service.outbox.order;

import com.example.payment.service.common.usecase.NoUseCaseHandler;
import com.example.payment.service.common.usecase.RegisterHelper;
import com.example.payment.service.outbox.OutboxStatus;
import com.example.payment.service.outbox.order.model.OutboxOrderMessage;
import com.example.payment.service.outbox.order.model.OutboxOrderPublishedSize;
import com.example.payment.service.outbox.order.port.OutboxOrderEventPort;
import com.example.payment.service.outbox.order.port.OutboxOrderPort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    private void updateOutboxOrderMessage(OutboxOrderMessage message, OutboxStatus status) {
        message.setOutboxStatus(status);
        outboxOrderPort.save(message);
    }
}
