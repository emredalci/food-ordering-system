package com.example.order.service.adapters.order.rest.dto;

import com.example.order.service.common.model.Money;
import com.example.order.service.common.model.StreetAddress;
import com.example.order.service.order.model.OrderItem;
import com.example.order.service.order.usecase.OrderCreateUseCase;
import com.example.order.service.product.model.Product;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderCreateRequest(@NotNull UUID customerId,
                                 @NotNull UUID restaurantId,
                                 @NotNull BigDecimal price,
                                 @NotNull List<OrderItemRequest> items,
                                 @NotNull OrderAddressRequest address) {

    public OrderCreateUseCase toUseCase(){
        return new OrderCreateUseCase(customerId, restaurantId, price, getOrderItemList(), getStreetAddress());
    }

    private StreetAddress getStreetAddress() {
        return new StreetAddress(UUID.randomUUID(), address.street(), address.postalCode(), address.city());
    }

    private List<OrderItem> getOrderItemList() {
        return this.items().stream().map(orderItemRequest ->
                OrderItem.builder()
                        .product(new Product(orderItemRequest.productId()))
                        .price(new Money(orderItemRequest.price()))
                        .quantity(orderItemRequest.quantity())
                        .subTotal(new Money(orderItemRequest.subTotal()))
                        .build()).toList();
    }
}
