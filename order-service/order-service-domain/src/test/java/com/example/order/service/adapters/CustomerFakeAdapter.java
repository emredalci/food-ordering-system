package com.example.order.service.adapters;

import com.example.order.service.common.exception.OrderServiceBusinessException;
import com.example.order.service.customer.port.CustomerPort;

import java.util.UUID;

public class CustomerFakeAdapter implements CustomerPort {

    public static final String id = "d215b5f8-0249-4dc5-89a3-51fd148cfb41";

    @Override
    public void isExist(UUID customerId) {
        if (customerId.equals(UUID.fromString(id))){
            return;
        }
        throw new OrderServiceBusinessException("customer.not.found");
    }
}
