package com.example.payment.service.payment.port;

import com.example.payment.service.payment.model.CreditEntry;
import com.example.payment.service.payment.model.CreditHistory;
import com.example.payment.service.payment.model.Payment;

import java.util.List;
import java.util.UUID;

public interface PaymentPort {

    CreditEntry getCreditEntry(UUID customerId);
    List<CreditHistory> getCreditHistory(UUID customerId);

    void save(Payment payment);

}
