package com.example.order.service.adapters.customer.jpa;

import com.example.order.service.customer.port.CustomerPort;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@ConditionalOnProperty(name = "adapters.customer.noob-data-adapter", havingValue = "true", matchIfMissing = false)
public class CustomerNoobAdapter implements CustomerPort {
    @Override
    public void isExist(UUID customerId) {

    }
}
