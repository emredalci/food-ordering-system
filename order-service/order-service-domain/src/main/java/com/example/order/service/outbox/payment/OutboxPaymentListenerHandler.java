package com.example.order.service.outbox.payment;

import com.example.order.service.common.DomainComponent;
import com.example.order.service.common.usecase.RegisterHelper;
import com.example.order.service.common.usecase.VoidUseCaseHandler;
import com.example.order.service.order.event.OrderCreatedEvent;
import com.example.order.service.order.event.OrderPaidEvent;
import com.example.order.service.order.model.Order;
import com.example.order.service.order.port.OrderPort;
import com.example.order.service.order.service.OrderService;
import com.example.order.service.outbox.payment.port.OutboxPaymentPort;
import com.example.order.service.outbox.payment.usecase.OutboxPaymentListenerUseCase;
import com.example.order.service.payment.model.PaymentStatus;
import com.example.order.service.saga.SagaStatus;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import lombok.extern.slf4j.Slf4j;

@DomainComponent
@Slf4j
public class OutboxPaymentListenerHandler extends RegisterHelper implements VoidUseCaseHandler<OutboxPaymentListenerUseCase> {

    private final OutboxPaymentPort outboxPaymentPort;
    private final OrderPort orderPort;

    private final OrderService orderService;

    public OutboxPaymentListenerHandler(OutboxPaymentPort outboxPaymentPort, OrderPort orderPort,
            OrderService orderService) {
        this.outboxPaymentPort = outboxPaymentPort;
        this.orderPort = orderPort;
        this.orderService = orderService;
        register(OutboxPaymentListenerUseCase.class, this);
    }

    @Transactional
    @Override
    public void handle(OutboxPaymentListenerUseCase useCase) {
        PaymentStatus paymentStatus = useCase.paymentStatus();
        if (paymentStatus.equals(PaymentStatus.COMPLETED)) {
            OrderCreatedEvent orderCreatedEvent = outboxPaymentPort.getBySagaIdAndSagaStatus(useCase.sagaId(), getSagaStatus(paymentStatus));
            Order order = orderPort.findById(useCase.orderId());
            OrderPaidEvent orderPaidEvent = orderService.payOrder(order);
            orderPort.save(order);
            SagaStatus sagaStatus = orderService.toSagaStatus(orderPaidEvent.order().getOrderStatus());
            orderCreatedEvent.setProcessedAt(LocalDateTime.now());
            orderCreatedEvent.setOrderStatus(orderPaidEvent.order().getOrderStatus());
            orderCreatedEvent.setSagaStatus(sagaStatus);
            outboxPaymentPort.save(orderCreatedEvent);
            //TODO: save outbox restaurant
            return;

        }

        if (paymentStatus.equals(PaymentStatus.CANCELLED) || paymentStatus.equals(PaymentStatus.FAILED)) {
            OrderCreatedEvent orderCreatedEvent = outboxPaymentPort.getBySagaIdAndSagaStatus(useCase.sagaId(), getSagaStatus(paymentStatus));
            Order order = orderPort.findById(useCase.orderId());
            orderService.cancelOrder(order, useCase.failureMessages());
            orderPort.save(order);
            SagaStatus sagaStatus = orderService.toSagaStatus(order.getOrderStatus());
            orderCreatedEvent.setProcessedAt(LocalDateTime.now());
            orderCreatedEvent.setOrderStatus(order.getOrderStatus());
            orderCreatedEvent.setSagaStatus(sagaStatus);
            outboxPaymentPort.save(orderCreatedEvent);
            if (PaymentStatus.CANCELLED.equals(paymentStatus)) {
                //TODO save outbox restaurant
            }
        }
    }

    private SagaStatus[] getSagaStatus(PaymentStatus paymentStatus) {
        return switch (paymentStatus) {
            case COMPLETED -> new SagaStatus[] { SagaStatus.STARTED };
            case CANCELLED -> new SagaStatus[] { SagaStatus.PROCESSING };
            case FAILED -> new SagaStatus[] { SagaStatus.STARTED, SagaStatus.PROCESSING };
        };
    }
}
