package com.example.order.service.adapters.outbox.payment.producer;

import com.example.order.service.configuration.KafkaTopicNameConfiguration;
import com.example.order.service.order.event.OrderCreatedEvent;
import com.example.order.service.outbox.OutboxStatus;
import com.example.order.service.outbox.payment.port.OutboxPaymentEventPort;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OutboxPaymentProducer implements OutboxPaymentEventPort {

    private final KafkaTopicNameConfiguration nameConfiguration;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    @Transactional
    public void publish(OrderCreatedEvent event, BiConsumer<OrderCreatedEvent, OutboxStatus> callback) {
        CompletableFuture<SendResult<String, String>> result =
                kafkaTemplate.send(nameConfiguration.getPaymentRequestTopicName(), event.getSagaId().toString(),
                        event.getPayload());

        result.whenComplete((complete, exception) -> {
            if (Objects.nonNull(exception)) {
                callback.accept(event, OutboxStatus.FAILED);
                log.error("Error has just occurred while sending {} id event", event.getId());
                result.completeExceptionally(exception);
            }
            callback.accept(event, OutboxStatus.COMPLETED);
            result.complete(complete);
        });


    }
}
