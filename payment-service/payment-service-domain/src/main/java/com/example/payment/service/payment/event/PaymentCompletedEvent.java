package com.example.payment.service.payment.event;

import com.example.payment.service.payment.model.Payment;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class PaymentCompletedEvent extends PaymentEvent {

    public static PaymentEvent of(Payment payment){
        return PaymentCompletedEvent.builder()
                .payment(payment)
                .build();
    }
}
