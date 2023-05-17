package com.example.payment.service;

import com.example.payment.service.common.DomainComponent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@ComponentScan(includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = {DomainComponent.class})})
@EnableJpaAuditing
@EnableFeignClients
public class PaymentServiceInfraApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceInfraApplication.class, args);
    }

}
