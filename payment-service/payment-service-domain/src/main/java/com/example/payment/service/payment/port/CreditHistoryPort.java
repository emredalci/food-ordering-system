package com.example.payment.service.payment.port;

import com.example.payment.service.payment.model.CreditHistory;

import java.util.List;
import java.util.UUID;

public interface CreditHistoryPort {

    void save(CreditHistory creditHistory);

    List<CreditHistory> getByCustomerId(UUID customerId);
}
