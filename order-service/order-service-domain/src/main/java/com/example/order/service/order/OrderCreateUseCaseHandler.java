package com.example.order.service.order;

import com.example.order.service.common.DomainComponent;
import com.example.order.service.common.usecase.RegisterHelper;
import com.example.order.service.common.usecase.UseCaseHandler;
import com.example.order.service.customer.port.CustomerPort;
import com.example.order.service.order.event.OrderCreatedEvent;
import com.example.order.service.order.model.Order;
import com.example.order.service.order.port.OrderPort;
import com.example.order.service.order.service.OrderService;
import com.example.order.service.order.usecase.OrderCreateUseCase;
import com.example.order.service.outbox.payment.port.OutboxPaymentPort;
import com.example.order.service.restaurant.model.Restaurant;
import com.example.order.service.restaurant.port.RestaurantPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@DomainComponent
public class OrderCreateUseCaseHandler extends RegisterHelper implements UseCaseHandler<Order, OrderCreateUseCase> {

    private final CustomerPort customerPort;
    private final RestaurantPort restaurantPort;
    private final OrderService orderService;
    private final OrderPort orderPort;
    private final OutboxPaymentPort outboxPaymentPort;

    public OrderCreateUseCaseHandler(CustomerPort customerPort,
                                     RestaurantPort restaurantPort,
                                     OrderService orderService,
                                     OrderPort orderPort, OutboxPaymentPort outboxPaymentPort) {
        this.restaurantPort = restaurantPort;
        this.customerPort = customerPort;
        this.orderService = orderService;
        this.orderPort = orderPort;
        this.outboxPaymentPort = outboxPaymentPort;
        register(OrderCreateUseCase.class, this);
    }

    @Override
    @Transactional
    public Order handler(OrderCreateUseCase useCase) {
        checkCustomer(useCase.customerId());
        Restaurant restaurant = getRestaurantInformation(useCase);
        Order order = useCase.toOrder();
        OrderCreatedEvent orderCreatedEvent = orderService.validateAndInitiateOrder(order, restaurant);
        Order savedOrder = orderPort.save(useCase);
        outboxPaymentPort.save(orderCreatedEvent);
        return savedOrder;
    }

    private void checkCustomer(UUID customerId) {
        customerPort.isExist(customerId);
    }

    private Restaurant getRestaurantInformation(OrderCreateUseCase useCase) {
        UUID restaurantId = useCase.restaurantId();
        List<UUID> productIdList = useCase.items().stream()
                .map(orderItem -> orderItem.getProduct().getId())
                .toList();

        return restaurantPort.retrieve(restaurantId, productIdList);
    }

}
