package com.example.payment.service.payment.port;

import com.example.payment.service.payment.model.CreditEntry;

import java.util.UUID;

public interface CreditEntryPort {

    void save(CreditEntry creditEntry);

    CreditEntry getByCustomerId(UUID customerId);
}
