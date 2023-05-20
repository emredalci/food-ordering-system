package com.example.payment.service.outbox.order;

import com.example.payment.service.common.DomainComponent;
import com.example.payment.service.common.usecase.NoUseCaseHandler;
import com.example.payment.service.common.usecase.RegisterHelper;
import com.example.payment.service.outbox.OutboxStatus;
import com.example.payment.service.outbox.order.model.OutboxOrderDeletedSize;
import com.example.payment.service.outbox.order.port.OutboxOrderPort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@DomainComponent
public class OutboxOrderCleanerSchedulerHandler extends RegisterHelper implements NoUseCaseHandler<OutboxOrderDeletedSize> {

    private final OutboxOrderPort outboxOrderPort;

    public OutboxOrderCleanerSchedulerHandler(OutboxOrderPort outboxOrderPort) {
        this.outboxOrderPort = outboxOrderPort;
        register(OutboxOrderDeletedSize.class, this);
    }

    @Override
    @Transactional
    public OutboxOrderDeletedSize handle() {
        List<UUID> messageIds = outboxOrderPort.getIdsByOutboxStatus(OutboxStatus.COMPLETED);
        if (messageIds.isEmpty()){
            return new OutboxOrderDeletedSize(0);
        }
        outboxOrderPort.deleteByIdList(messageIds);
        return new OutboxOrderDeletedSize(messageIds.size());
    }
}
