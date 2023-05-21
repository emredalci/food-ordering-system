package com.example.resturant.service.adapter.product.model;

import java.math.BigDecimal;

public record ProductRequest(String id,
                             String name,
                             BigDecimal price,
                             int quantity,
                             boolean available) {


}
