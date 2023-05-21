package com.example.resturant.service.adapter.restaurant.listener.model;

import com.example.restaurant.service.common.exception.RestaurantServiceBusinessException;
import com.example.restaurant.service.common.model.Money;
import com.example.restaurant.service.product.Product;
import com.example.restaurant.service.restaurant.model.OrderStatus;
import com.example.restaurant.service.restaurant.usecase.RestaurantListenerUseCase;
import com.example.resturant.service.adapter.product.model.ProductRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record RestaurantRequest(String id,
                                String sagaId,
                                String restaurantId,
                                String orderId,
                                String orderStatus,
                                List<ProductRequest> productRequestList,
                                BigDecimal price,
                                LocalDateTime createdAt) {

    public RestaurantListenerUseCase toUseCase() {
        return new RestaurantListenerUseCase(UUID.fromString(id),
                UUID.fromString(sagaId),
                UUID.fromString(restaurantId),
                UUID.fromString(orderId),
                toOrderStatus(orderStatus),
                toProducts(productRequestList),
                new Money(price),
                createdAt);
    }

    private List<Product> toProducts(List<ProductRequest> productRequestList) {
        return productRequestList.stream().map(request ->
                Product.builder()
                        .id(UUID.fromString(request.id()))
                        .name(request.name())
                        .price(new Money(request.price()))
                        .quantity(request.quantity())
                        .available(request.available())
                        .build()).toList();
    }

    private OrderStatus toOrderStatus(String orderStatus) {
        return switch (orderStatus) {
            case "PAID" -> OrderStatus.PAID;
            default -> throw new RestaurantServiceBusinessException("order.status.not.valid");
        };
    }


}
