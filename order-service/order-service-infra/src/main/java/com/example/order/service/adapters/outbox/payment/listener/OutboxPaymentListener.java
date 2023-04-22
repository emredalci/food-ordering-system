package com.example.order.service.adapters.outbox.payment.listener;

import com.example.order.service.adapters.outbox.payment.listener.model.PaymentResponse;
import com.example.order.service.common.rest.BaseController;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OutboxPaymentListener extends BaseController {

    @KafkaListener(topics = "${order-service.payment-response-topic-name}")
    void receive(@Payload List<PaymentResponse> messages) {

        messages.forEach(message -> {
            try {
                publish(message.toUseCase());
            } catch (OptimisticLockingFailureException e) {
                log.info("Caught optimistic locking exception, order id: {}", message.orderId());
            }
        });

    }
}
