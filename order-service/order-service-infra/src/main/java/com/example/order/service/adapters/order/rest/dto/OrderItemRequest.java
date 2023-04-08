package com.example.order.service.adapters.order.rest.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemRequest(@NotNull UUID productId,
                               @NotNull Integer quantity,
                               @NotNull BigDecimal price,
                               @NotNull BigDecimal subTotal) {
}
