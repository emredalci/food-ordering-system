package com.example.payment.service.adapters.outbox.payment.producer;

import com.example.payment.service.configuration.KafkaTopicNameConfiguration;
import com.example.payment.service.outbox.OutboxStatus;
import com.example.payment.service.outbox.order.model.OutboxOrderMessage;
import com.example.payment.service.outbox.order.port.OutboxOrderEventPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

@Service
@Slf4j
@RequiredArgsConstructor
public class OutboxOrderProducer implements OutboxOrderEventPort {

    private final KafkaTopicNameConfiguration nameConfiguration;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    @Transactional
    public void publish(OutboxOrderMessage outboxOrderMessage, BiConsumer<OutboxOrderMessage, OutboxStatus> callback) {
        CompletableFuture<SendResult<String, String>> result = kafkaTemplate.send(nameConfiguration.getPaymentResponseTopicName(),
                outboxOrderMessage.getSagaId().toString(), outboxOrderMessage.getPayload());

        result.whenComplete((complete, exception) -> {
            if (Objects.nonNull(exception)){
                callback.accept(outboxOrderMessage, OutboxStatus.FAILED);
                log.error("Error has just occurred while sending {} id event", outboxOrderMessage.getId());
                result.completeExceptionally(exception);
            }
            callback.accept(outboxOrderMessage, OutboxStatus.COMPLETED);
            result.complete(complete);
        });
    }
}
