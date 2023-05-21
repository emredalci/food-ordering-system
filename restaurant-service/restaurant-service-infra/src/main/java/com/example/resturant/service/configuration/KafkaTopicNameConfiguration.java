package com.example.resturant.service.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "restaurant-service")
@Data
public class KafkaTopicNameConfiguration {

    private String restaurantRequestTopicName;
    private String restaurantResponseTopicName;
}
