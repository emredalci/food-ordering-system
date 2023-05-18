package com.example.payment.service.payment.model;

import com.example.payment.service.common.model.Money;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Builder
@Getter
public class Payment {

    private UUID id;
    private final UUID orderId;
    private final UUID customerId;
    private final Money price;

    private PaymentStatus paymentStatus;
    private LocalDateTime createdAt;


    public void validatePayment(List<String> failureMessages) {
        if (Objects.isNull(price) || Boolean.FALSE.equals(price.isGreaterThanZero())){
            failureMessages.add("Total price must be greater than zero");
        }
    }

    public void initializePayment() {
        id = UUID.randomUUID();
        createdAt = LocalDateTime.now();
    }

    public void updateStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
