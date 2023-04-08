package com.example.order.service.adapters.customer.jpa;

import com.example.order.service.adapters.customer.jpa.repository.CustomerJpaRepository;
import com.example.order.service.common.exception.OrderServiceBusinessException;
import com.example.order.service.customer.port.CustomerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "adapters.customer.noob-data-adapter", havingValue = "false", matchIfMissing = true)
public class CustomerDataAdapter implements CustomerPort {

    private final CustomerJpaRepository customerJpaRepository;


    @Override
    public void isExist(UUID customerId) {
        if (Boolean.FALSE.equals(customerJpaRepository.existsById(customerId))){
            throw new OrderServiceBusinessException("customer.not.found");
        }
    }
}
