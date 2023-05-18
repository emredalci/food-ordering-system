package com.example.payment.service.payment.port;

import com.example.payment.service.payment.model.CreditHistory;

public interface CreditHistoryPort {

    void save(CreditHistory creditHistory);
}
