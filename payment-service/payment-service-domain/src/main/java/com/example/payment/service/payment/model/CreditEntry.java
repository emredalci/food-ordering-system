package com.example.payment.service.payment.model;

import com.example.payment.service.common.model.Money;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class CreditEntry {

    private UUID id;
    private final UUID customerId;
    private Money totalCreditAmount;

    public void subtractCreditAmount(Money amount) {
        totalCreditAmount = totalCreditAmount.subtract(amount);
    }

}
