package com.example.order.service.outbox.payment;

import com.example.order.service.common.DomainComponent;
import com.example.order.service.common.usecase.NoUseCaseHandler;
import com.example.order.service.common.usecase.RegisterHelper;
import com.example.order.service.outbox.payment.model.DeletedEventSize;
import com.example.order.service.outbox.payment.port.OutboxPaymentPort;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DomainComponent
public class OutboxPaymentCleanerSchedulerHandler extends RegisterHelper implements NoUseCaseHandler<DeletedEventSize> {

    private final OutboxPaymentPort outboxPaymentPort;

    public OutboxPaymentCleanerSchedulerHandler(OutboxPaymentPort outboxPaymentPort) {
        this.outboxPaymentPort = outboxPaymentPort;
        register(DeletedEventSize.class, this);
    }

    @Override
    @Transactional
    public DeletedEventSize handle() {
        List<UUID> deleteReadyEventIds = outboxPaymentPort.getDeleteReadyEventIds();
        if (deleteReadyEventIds.isEmpty()){
            return new DeletedEventSize(0);
        }
        outboxPaymentPort.deleteByIdList(deleteReadyEventIds);
        return new DeletedEventSize(deleteReadyEventIds.size());
    }
}
