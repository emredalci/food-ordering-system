package com.example.payment.service.payment.service.strategy.impl;

import com.example.payment.service.common.DomainComponent;
import com.example.payment.service.common.exception.PaymentServiceBusinessException;
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

import java.util.List;

@DomainComponent
public class CancelPaymentImpl implements PaymentStatus {

    private final PaymentPort paymentPort;
    private final CreditEntryPort creditEntryPort;
    private final CreditHistoryPort creditHistoryPort;
    private final PaymentService paymentService;
    private final ObjectMapper objectMapper;
    private final OutboxOrderPort outboxOrderPort;

    public CancelPaymentImpl(PaymentPort paymentPort, CreditEntryPort creditEntryPort, CreditHistoryPort creditHistoryPort, PaymentService paymentService, ObjectMapper objectMapper, OutboxOrderPort outboxOrderPort) {
        this.paymentPort = paymentPort;
        this.creditEntryPort = creditEntryPort;
        this.creditHistoryPort = creditHistoryPort;
        this.paymentService = paymentService;
        this.objectMapper = objectMapper;
        this.outboxOrderPort = outboxOrderPort;
    }


    @Override
    public void execute(PaymentListenerUseCase useCase) {
        //publishIfOutboxMessageProcessedForPayment
        Payment payment = paymentPort.getByOrderId(useCase.orderId());
        CreditEntry creditEntry = creditEntryPort.getByCustomerId(useCase.customerId());
        List<CreditHistory> creditHistories = creditHistoryPort.getByCustomerId(useCase.customerId());
        PaymentEvent event = paymentService.validateAndCancelPayment(payment, creditEntry, creditHistories);
        paymentPort.save(payment);
        if (event.getFailureMessages().isEmpty()){
            creditEntryPort.save(creditEntry);
            creditHistoryPort.save(creditHistories.get(creditHistories.size() - 1));
        }
        OutboxOrderMessage outboxOrderMessage = OutboxOrderMessage.of(event, createPayload(event), useCase.sagaId());
        outboxOrderPort.save(outboxOrderMessage);
    }

    @Override
    public PaymentOrderStatus getName() {
        return PaymentOrderStatus.CANCELLED;
    }

    private String createPayload(PaymentEvent event) {
        OutboxOrderEventPayload payload = OutboxOrderEventPayload.of(event);
        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new PaymentServiceBusinessException("payload.not.created");
        }
    }
}
