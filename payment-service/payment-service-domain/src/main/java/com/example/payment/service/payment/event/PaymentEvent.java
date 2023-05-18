package com.example.payment.service.payment.event;

import com.example.payment.service.common.event.DomainEvent;
import com.example.payment.service.payment.model.Payment;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class PaymentEvent extends DomainEvent {

    private Payment payment;

}
