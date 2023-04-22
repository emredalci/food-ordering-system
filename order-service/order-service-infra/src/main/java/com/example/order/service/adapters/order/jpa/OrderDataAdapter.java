package com.example.order.service.adapters.order.jpa;

import com.example.order.service.adapters.order.jpa.entity.OrderEntity;
import com.example.order.service.adapters.order.jpa.repository.OrderJpaRepository;
import com.example.order.service.adapters.order.mapper.OrderMapper;
import com.example.order.service.common.exception.OrderServiceBusinessException;
import com.example.order.service.order.model.Order;
import com.example.order.service.order.port.OrderPort;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "adapters.order.noob-data-adapter", havingValue = "false", matchIfMissing = true)
public class OrderDataAdapter implements OrderPort {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    @Transactional
    public void save(Order order) {
        OrderEntity orderEntity = OrderMapper.INSTANCE.map(order);
        orderJpaRepository.save(orderEntity);
    }

    @Override
    public Order findById(UUID id) {
        return orderJpaRepository.findById(id)
                .map(OrderMapper.INSTANCE::map)
                .orElseThrow(() -> new OrderServiceBusinessException("not.found.order"));
    }

    @Override
    public Order findByTrackingId(UUID trackingId) {
        return orderJpaRepository.findByTrackingId(trackingId)
                .map(OrderMapper.INSTANCE::map)
                .orElseThrow(() -> new OrderServiceBusinessException("not.found.tracking.id"));
    }
}
