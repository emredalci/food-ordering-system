package com.example.order.service.customer.port;

import java.util.UUID;

public interface CustomerPort {

    void isExist(UUID customerId);
}
