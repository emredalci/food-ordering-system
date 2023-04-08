package com.example.order.service.order.port;

import com.example.order.service.order.event.OrderCreatedEvent;

public interface OrderEventPort {

    void publish(OrderCreatedEvent orderCreatedEvent);

}
