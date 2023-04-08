package com.example.order.service.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "order-service")
@Data
public class KafkaTopicNameConfiguration {

    private String paymentRequestTopicName;
    private String paymentResponseTopicName;
    private String restaurantApprovalRequestTopicName;
    private String restaurantApprovalResponseTopicName;
}
