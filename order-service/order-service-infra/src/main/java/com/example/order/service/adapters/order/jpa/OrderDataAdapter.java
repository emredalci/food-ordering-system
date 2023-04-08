package com.example.order.service.adapters.order.jpa;

import com.example.order.service.adapters.order.jpa.entity.OrderEntity;
import com.example.order.service.adapters.order.jpa.repository.OrderJpaRepository;
import com.example.order.service.adapters.order.mapper.OrderMapper;
import com.example.order.service.common.exception.OrderServiceBusinessException;
import com.example.order.service.order.model.Order;
import com.example.order.service.order.port.OrderPort;
import com.example.order.service.order.usecase.OrderCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "adapters.order.noob-data-adapter", havingValue = "false", matchIfMissing = true)
public class OrderDataAdapter implements OrderPort {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order save(OrderCreateUseCase useCase) {
        Order order = useCase.toOrder();
        OrderEntity orderEntity = OrderMapper.INSTANCE.map(order);
        OrderEntity savedEntity = orderJpaRepository.save(orderEntity);
        return OrderMapper.INSTANCE.map(savedEntity);

    }

    @Override
    public Order findByTrackingId(UUID trackingId) {
        return orderJpaRepository.findByTrackingId(trackingId).map(OrderMapper.INSTANCE::map)
                .orElseThrow(() -> new OrderServiceBusinessException("not.found.tracking.id"));
    }
}
