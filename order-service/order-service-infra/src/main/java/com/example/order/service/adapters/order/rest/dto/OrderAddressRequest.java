package com.example.order.service.adapters.order.rest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

public record OrderAddressRequest(@NotNull @Max(value = 50) String street,
                                  @NotNull @Max(value = 10) String postalCode,
                                  @NotNull @Max(value = 50) String city) {
}
