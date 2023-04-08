package com.example.order.service.order;

import com.example.order.service.common.DomainComponent;
import com.example.order.service.common.usecase.RegisterHelper;
import com.example.order.service.common.usecase.UseCaseHandler;
import com.example.order.service.order.model.Order;
import com.example.order.service.order.port.OrderPort;
import com.example.order.service.order.usecase.OrderTrackUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@DomainComponent
public class OrderTrackUseCaseHandler extends RegisterHelper implements UseCaseHandler<Order, OrderTrackUseCase> {

    private final OrderPort orderPort;

    public OrderTrackUseCaseHandler(OrderPort orderPort) {
        this.orderPort = orderPort;
        register(OrderTrackUseCase.class, this);
    }

    @Override
    @Transactional(readOnly = true)
    public Order handler(OrderTrackUseCase useCase) {
        return orderPort.findByTrackingId(useCase.orderTrackingId());
    }
}
