package com.example.payment.service.payment.service.strategy.impl;

import com.example.payment.service.common.DomainComponent;
import com.example.payment.service.common.exception.PaymentServiceBusinessException;
import com.example.payment.service.common.model.Money;
import com.example.payment.service.outbox.OutboxStatus;
import com.example.payment.service.outbox.order.model.OutboxOrderEventPayload;
import com.example.payment.service.outbox.order.model.OutboxOrderMessage;
import com.example.payment.service.outbox.order.port.OutboxOrderPort;
import com.example.payment.service.payment.event.PaymentEvent;
import com.example.payment.service.payment.model.CreditEntry;
import com.example.payment.service.payment.model.CreditHistory;
import com.example.payment.service.payment.model.Payment;
import com.example.payment.service.payment.model.PaymentOrderStatus;
import com.example.payment.service.payment.port.CreditEntryPort;
import com.example.payment.service.payment.port.CreditHistoryPort;
import com.example.payment.service.payment.port.PaymentPort;
import com.example.payment.service.payment.service.PaymentService;
import com.example.payment.service.payment.service.strategy.PaymentStatus;
import com.example.payment.service.payment.usecase.PaymentListenerUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@DomainComponent
public class CompletePaymentImpl implements PaymentStatus {

    private final PaymentPort paymentPort;
    private final PaymentService paymentService;
    private final CreditEntryPort creditEntryPort;
    private final CreditHistoryPort creditHistoryPort;
    private final OutboxOrderPort outboxOrderPort;
    private final ObjectMapper objectMapper;

    public CompletePaymentImpl(PaymentPort paymentPort, PaymentService paymentService, CreditEntryPort creditEntryPort, CreditHistoryPort creditHistoryPort, OutboxOrderPort outboxOrderPort, ObjectMapper objectMapper) {
        this.paymentPort = paymentPort;
        this.paymentService = paymentService;
        this.creditEntryPort = creditEntryPort;
        this.creditHistoryPort = creditHistoryPort;
        this.outboxOrderPort = outboxOrderPort;
        this.objectMapper = objectMapper;
    }


    @Override
    @Transactional
    public void execute(PaymentListenerUseCase useCase) {
        //publishIfOutboxMessageProcessedForPayment
        Payment payment = buildPayment(useCase);
        CreditEntry creditEntry = creditEntryPort.getByCustomerId(useCase.customerId());
        List<CreditHistory> creditHistories = creditHistoryPort.getByCustomerId(useCase.customerId());
        PaymentEvent event = paymentService.validateAndInitiatePayment(payment, creditEntry, creditHistories);
        paymentPort.save(payment);
        if (event.getFailureMessages().isEmpty()) {
            creditEntryPort.save(creditEntry);
            creditHistoryPort.save(creditHistories.get(creditHistories.size() - 1));
        }
        OutboxOrderMessage outboxOrderMessage = buildOutboxOrderMessage(event, useCase.sagaId());
        outboxOrderPort.save(outboxOrderMessage);
    }

    @Override
    public PaymentOrderStatus getName() {
        return PaymentOrderStatus.PENDING;
    }

    private OutboxOrderMessage buildOutboxOrderMessage(PaymentEvent event, UUID sagaId) {
        return OutboxOrderMessage.builder()
                .id(UUID.randomUUID())
                .sagaId(sagaId)
                .createdAt(event.getCreatedAt())
                .processedAt(LocalDateTime.now())
                .payload(createPayload(event))
                .paymentStatus(event.getPayment().getPaymentStatus())
                .outboxStatus(OutboxStatus.STARTED)
                .build();
    }

    private String createPayload(PaymentEvent event) {
        OutboxOrderEventPayload payload = OutboxOrderEventPayload.of(event);
        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new PaymentServiceBusinessException("payload.not.created");
        }
    }

    private Payment buildPayment(PaymentListenerUseCase useCase) {
        return Payment.builder()
                .orderId(useCase.orderId())
                .customerId(useCase.customerId())
                .price(new Money(useCase.price()))
                .build();
    }
}
