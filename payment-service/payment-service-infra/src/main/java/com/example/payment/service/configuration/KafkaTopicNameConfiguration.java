package com.example.payment.service.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "payment-service")
@Data
public class KafkaTopicNameConfiguration {

    private String paymentRequestTopicName;
    private String paymentResponseTopicName;
}
