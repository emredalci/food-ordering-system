package com.example.order.service.adapters.outbox.payment.jpa;

import com.example.order.service.adapters.outbox.payment.jpa.entity.PaymentOutboxEntity;
import com.example.order.service.adapters.outbox.payment.jpa.repository.PaymentOutboxJpaRepository;
import com.example.order.service.adapters.outbox.payment.mapper.PaymentOutboxMapper;
import com.example.order.service.order.event.OrderCreatedEvent;
import com.example.order.service.outbox.payment.port.PaymentOutboxPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentOutboxDataAdapter implements PaymentOutboxPort {

    private final PaymentOutboxJpaRepository repository;

    @Override
    public void save(OrderCreatedEvent orderCreatedEvent) {
        PaymentOutboxEntity entity = PaymentOutboxMapper.INSTANCE.map(orderCreatedEvent);
        repository.save(entity);
    }
}
