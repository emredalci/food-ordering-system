package com.example.payment.service.payment;

import com.example.payment.service.common.DomainComponent;
import com.example.payment.service.common.exception.PaymentServiceBusinessException;
import com.example.payment.service.common.model.Money;
import com.example.payment.service.common.usecase.RegisterHelper;
import com.example.payment.service.common.usecase.VoidUseCaseHandler;
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
import com.example.payment.service.payment.usecase.PaymentListenerUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@DomainComponent
@Slf4j
public class PaymentListenerHandler extends RegisterHelper implements VoidUseCaseHandler<PaymentListenerUseCase> {

    private final PaymentPort paymentPort;
    private final PaymentService paymentService;
    private final CreditEntryPort creditEntryPort;
    private final CreditHistoryPort creditHistoryPort;
    private final ObjectMapper objectMapper;
    private final OutboxOrderPort outboxOrderPort;

    public PaymentListenerHandler(PaymentPort paymentPort,
                                  PaymentService paymentService,
                                  CreditEntryPort creditEntryPort,
                                  CreditHistoryPort creditHistoryPort,
                                  ObjectMapper objectMapper,
                                  OutboxOrderPort outboxOrderPort) {
        this.paymentPort = paymentPort;
        this.paymentService = paymentService;
        this.creditEntryPort = creditEntryPort;
        this.creditHistoryPort = creditHistoryPort;
        this.objectMapper = objectMapper;
        this.outboxOrderPort = outboxOrderPort;
        register(PaymentListenerUseCase.class, this);
    }

    @Transactional
    @Override
    public void handle(PaymentListenerUseCase useCase) {
        PaymentOrderStatus paymentOrderStatus = useCase.orderStatus();
        if (paymentOrderStatus.equals(PaymentOrderStatus.PENDING)) {
            Payment payment = buildPayment(useCase);
            CreditEntry creditEntry = paymentPort.getCreditEntry(useCase.customerId());
            List<CreditHistory> creditHistories = paymentPort.getCreditHistory(useCase.customerId());
            PaymentEvent event = paymentService.validateAndInitiatePayment(payment, creditEntry, creditHistories);
            paymentPort.save(payment);
            if (event.getFailureMessages().isEmpty()) {
                creditEntryPort.save(creditEntry);
                creditHistoryPort.save(creditHistories.get(creditHistories.size() - 1));
            }
            OutboxOrderMessage outboxOrderMessage = buildOutboxOrderMessage(event, useCase.sagaId());
            outboxOrderPort.save(outboxOrderMessage);
        }
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
        OutboxOrderEventPayload orderEventPayload = OutboxOrderEventPayload.builder()
                .paymentId(event.getPayment().getId().toString())
                .customerId(event.getPayment().getCustomerId().toString())
                .orderId(event.getPayment().getOrderId().toString())
                .price(event.getPayment().getPrice().amount())
                .paymentStatus(event.getPayment().getPaymentStatus().name())
                .createdAt(event.getCreatedAt())
                .failureMessages(event.getFailureMessages())
                .build();

        try {
            return objectMapper.writeValueAsString(orderEventPayload);
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
