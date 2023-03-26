package com.example.order.service.common.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Money(BigDecimal amount) {

    public boolean isGreaterThanZero(){
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isGreaterThan(Money money){
        return amount != null && amount.compareTo(money.amount()) > 0;
    }

    public Money add(Money money){
        return new Money(scale(amount.add(money.amount())));
    }

    public Money subtract(Money money){
        return new Money(scale(amount.subtract(money.amount())));
    }

    public Money multiply(int multiplier) {
        return new Money(scale(amount.multiply(BigDecimal.valueOf(multiplier))));
    }


    public BigDecimal scale(BigDecimal input){
        return input.setScale(2, RoundingMode.HALF_EVEN);
    }
}
