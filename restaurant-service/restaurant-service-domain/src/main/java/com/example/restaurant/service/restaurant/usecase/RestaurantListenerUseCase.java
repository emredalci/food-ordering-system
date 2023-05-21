package com.example.restaurant.service.restaurant.usecase;

import com.example.restaurant.service.common.model.Money;
import com.example.restaurant.service.common.usecase.UseCase;
import com.example.restaurant.service.product.Product;
import com.example.restaurant.service.restaurant.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public record RestaurantListenerUseCase (UUID id,
                                         UUID sagaId,
                                         UUID restaurantId,
                                         UUID orderId,
                                         OrderStatus orderStatus,
                                         List<Product> products,
                                         Money price,
                                         LocalDateTime createdAt) implements UseCase {


}
