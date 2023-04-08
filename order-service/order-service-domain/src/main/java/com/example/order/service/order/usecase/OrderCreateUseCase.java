package com.example.order.service.order.usecase;

import com.example.order.service.common.model.Money;
import com.example.order.service.common.model.StreetAddress;
import com.example.order.service.common.usecase.UseCase;
import com.example.order.service.order.model.Order;
import com.example.order.service.order.model.OrderItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderCreateUseCase(UUID customerId,
                                 UUID restaurantId,
                                 BigDecimal price,
                                 List<OrderItem> items,
                                 StreetAddress address) implements UseCase {

    public Order toOrder() {
        return Order.builder()
                .customerId(customerId)
                .restaurantId(restaurantId)
                .deliveryAddress(address)
                .price(new Money(price))
                .items(items)
                .build();
    }
}
