package com.example.payment.service.payment.port;

import com.example.payment.service.payment.model.Payment;

import java.util.UUID;

public interface PaymentPort {


    void save(Payment payment);

    Payment getByOrderId(UUID orderId);

}
