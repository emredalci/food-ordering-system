package com.example.order.service.order.model;

import com.example.order.service.common.model.Money;
import com.example.order.service.product.model.Product;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class OrderItem {
    private long id;
    private UUID orderId;
    private final Product product;
    private final int quantity;
    private final Money price;
    private final Money subTotal;

    void initializeOrderItem(long id, UUID orderId) {
        this.id = id;
        this.orderId = orderId;
    }

    boolean isPriceValid() {
        return price.isGreaterThanZero() &&
                price.equals(product.getPrice()) &&
                price.multiply(quantity).equals(subTotal);
    }
}
