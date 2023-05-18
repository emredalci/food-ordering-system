package com.example.payment.service.adapters.payment.listener;

import com.example.payment.service.adapters.payment.listener.model.PaymentRequest;
import com.example.payment.service.common.rest.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PaymentListener extends BaseController {

    @KafkaListener(topics = "${payment-service.payment-request-topic-name}")
    void receive(@Payload List<PaymentRequest> messages) {
        messages.forEach(message -> publish(message.toUseCase()));
    }
}
