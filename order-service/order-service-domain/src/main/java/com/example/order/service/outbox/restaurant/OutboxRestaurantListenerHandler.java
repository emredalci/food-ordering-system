package com.example.order.service.outbox.restaurant;

import com.example.order.service.common.DomainComponent;
import com.example.order.service.common.usecase.RegisterHelper;
import com.example.order.service.common.usecase.VoidUseCaseHandler;
import com.example.order.service.order.event.OrderCancelledEvent;
import com.example.order.service.order.event.OrderCreatedEvent;
import com.example.order.service.order.event.OrderPaidEvent;
import com.example.order.service.order.model.Order;
import com.example.order.service.order.model.RestaurantApprovalStatus;
import com.example.order.service.order.port.OrderPort;
import com.example.order.service.order.service.OrderService;
import com.example.order.service.outbox.OutboxStatus;
import com.example.order.service.outbox.payment.model.PaymentPayload;
import com.example.order.service.outbox.payment.port.OutboxPaymentPort;
import com.example.order.service.outbox.restaurant.port.OutboxRestaurantPort;
import com.example.order.service.outbox.restaurant.usecase.OutboxRestaurantListenerUseCase;
import com.example.order.service.saga.SagaStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@DomainComponent
@Slf4j
public class OutboxRestaurantListenerHandler extends RegisterHelper implements VoidUseCaseHandler<OutboxRestaurantListenerUseCase> {

    private final OutboxRestaurantPort outboxRestaurantPort;
    private final OutboxPaymentPort outboxPaymentPort;
    private final OrderPort orderPort;
    private final OrderService orderService;

    public OutboxRestaurantListenerHandler(OutboxRestaurantPort outboxRestaurantPort, OutboxPaymentPort outboxPaymentPort, OrderPort orderPort, OrderService orderService) {
        this.outboxRestaurantPort = outboxRestaurantPort;
        this.outboxPaymentPort = outboxPaymentPort;
        this.orderPort = orderPort;
        this.orderService = orderService;
        register(OutboxRestaurantListenerUseCase.class, this);
    }

    @Override
    @Transactional
    public void handle(OutboxRestaurantListenerUseCase useCase) {
        RestaurantApprovalStatus restaurantStatus = useCase.restaurantApprovalStatus();
        if (restaurantStatus.equals(RestaurantApprovalStatus.APPROVED)) {
            OrderPaidEvent orderPaidEvent = outboxRestaurantPort.getBySagaIdAndSagaStatus(useCase.sagaId(), SagaStatus.PROCESSING);
            Order order = orderPort.findById(useCase.orderId());
            orderService.approveOrder(order);
            orderPort.save(order);
            SagaStatus sagaStatus = orderService.toSagaStatus(order.getOrderStatus());
            orderPaidEvent.setProcessedAt(LocalDateTime.now());
            orderPaidEvent.setOrderStatus(order.getOrderStatus());
            orderPaidEvent.setSagaStatus(sagaStatus);
            outboxRestaurantPort.save(orderPaidEvent);
            OrderCreatedEvent orderCreatedEvent = outboxPaymentPort.getBySagaIdAndSagaStatus(orderPaidEvent.getSagaId(), sagaStatus);
            orderCreatedEvent.setProcessedAt(LocalDateTime.now());
            orderCreatedEvent.setOrderStatus(order.getOrderStatus());
            orderCreatedEvent.setSagaStatus(sagaStatus);
            outboxPaymentPort.save(orderCreatedEvent);
        }

        if (restaurantStatus.equals(RestaurantApprovalStatus.REJECTED)) {
            OrderPaidEvent orderPaidEvent = outboxRestaurantPort.getBySagaIdAndSagaStatus(useCase.sagaId(), SagaStatus.PROCESSING);
            Order order = orderPort.findById(useCase.orderId());
            OrderCancelledEvent orderCancelledEvent = orderService.cancelOrderPayment(order, useCase.failureMessages());
            orderPort.save(order);
            SagaStatus sagaStatus = orderService.toSagaStatus(order.getOrderStatus());
            orderPaidEvent.setProcessedAt(LocalDateTime.now());
            orderPaidEvent.setOrderStatus(order.getOrderStatus());
            orderPaidEvent.setSagaStatus(sagaStatus);
            OrderCreatedEvent orderCreatedEvent = OrderCreatedEvent.builder()
                    .id(UUID.randomUUID())
                    .sagaId(orderPaidEvent.getSagaId())
                    .createdAt(LocalDateTime.now())
                    .payload(PaymentPayload.fromOrder(order).toString())
                    .orderStatus(orderCancelledEvent.getOrderStatus())
                    .sagaStatus(sagaStatus)
                    .outboxStatus(OutboxStatus.STARTED)
                    .build();
            outboxPaymentPort.save(orderCreatedEvent);
        }
    }
}
