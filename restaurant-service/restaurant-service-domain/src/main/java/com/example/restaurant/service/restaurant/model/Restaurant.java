package com.example.restaurant.service.restaurant.model;

import com.example.restaurant.service.common.model.Money;
import com.example.restaurant.service.product.Product;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class Restaurant {

    private UUID id;
    private UUID orderId;
    private OrderApprovalStatus orderApprovalStatus;
    private boolean active;
    private OrderStatus orderStatus;
    private Money totalAmount;
    private List<Product> products;

    public void validateOrder(List<String> failureMessages) {
        if (Boolean.FALSE.equals(orderStatus.equals(OrderStatus.PAID))) {
            failureMessages.add(String.format("Payment is not completed for order: %s " , orderId.toString()));
        }

        Money calculatedTotalAmount = products.stream().map(product -> {
            if (Boolean.FALSE.equals(product.isAvailable())) {
                failureMessages.add(String.format("Product with id : %s is not available", product.getId().toString()));
            }
            return product.getPrice().multiply(product.getQuantity());
        }).reduce(Money.ZERO, Money::add);

        if (Boolean.FALSE.equals(calculatedTotalAmount.isEqual(this.totalAmount))) {
            failureMessages.add(String.format("Price total is not correct for order : %s", orderId.toString()));
        }
    }

    public void setOrderApprovalStatus(OrderApprovalStatus orderApprovalStatus) {
        this.orderApprovalStatus = orderApprovalStatus;
    }
}
