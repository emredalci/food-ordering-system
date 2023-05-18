package com.example.payment.service.payment.model;

import com.example.payment.service.common.model.Money;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class CreditHistory {

    private UUID id;
    private final UUID customerId;
    private final Money amount;
    private final TransactionType transactionType;
}
