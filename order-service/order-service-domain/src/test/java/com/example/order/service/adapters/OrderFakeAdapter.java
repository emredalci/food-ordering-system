package com.example.order.service.adapters;

import com.example.order.service.common.model.Money;
import com.example.order.service.common.model.StreetAddress;
import com.example.order.service.order.model.Order;
import com.example.order.service.order.model.OrderItem;
import com.example.order.service.order.model.OrderStatus;
import com.example.order.service.order.port.OrderPort;
import com.example.order.service.product.model.Product;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@Slf4j
public class OrderFakeAdapter implements OrderPort {

    @Override
    public void save(Order order) {
        log.info("Order is saved");
    }

    @Override
    public Order findById(UUID id) {
        OrderItem item1 = buildOrderItem(UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb48"),
                1,
                new Money(new BigDecimal("50.00")),
                new Money(new BigDecimal("50.00")));

        OrderItem item2 = buildOrderItem(UUID.fromString("c05c5b14-ce68-4237-b4c0-4b95d397ac09"),
                3,
                new Money(new BigDecimal("50.00")),
                new Money(new BigDecimal("150.00")));
        return Order.builder()
                .id(UUID.randomUUID())
                .customerId(UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb41"))
                .restaurantId(UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb45"))
                .deliveryAddress(new StreetAddress(UUID.fromString("19cc9a3a-bd29-443f-a447-d221e1790641"), "street_1", "1000AB",
                        "Amsterdam"))
                .price(new Money(new BigDecimal("200.00")))
                .items(List.of(item1, item2))
                .trackingId(UUID.randomUUID())
                .orderStatus(OrderStatus.PENDING)
                .failureMessages(List.of())
                .build();
    }

    @Override
    public Order findByTrackingId(UUID trackingId) {

        return Order.builder()
                .trackingId(trackingId)
                .orderStatus(OrderStatus.PENDING)
                .build();
    }

    private OrderItem buildOrderItem(UUID id, int quantity, Money price, Money subtotal) {
        return OrderItem.builder()
                .product(new Product(id))
                .quantity(quantity)
                .price(price)
                .subTotal(subtotal)
                .build();
    }
}
