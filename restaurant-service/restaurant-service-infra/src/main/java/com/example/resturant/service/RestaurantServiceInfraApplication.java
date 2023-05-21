package com.example.resturant.service;

import com.example.restaurant.service.common.DomainComponent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@ComponentScan(includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = {DomainComponent.class})})
@EnableJpaAuditing
public class RestaurantServiceInfraApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantServiceInfraApplication.class, args);
    }

}
