package com.example.payment.service.payment.port;

import com.example.payment.service.payment.model.CreditEntry;

public interface CreditEntryPort {

    void save(CreditEntry creditEntry);
}
