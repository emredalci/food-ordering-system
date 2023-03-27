package com.example.order.service.common.model;

import java.util.UUID;

public record StreetAddress(UUID id, String street, String postCode, String city) {
}
