package com.example.payment.service.payment.service;

import com.example.payment.service.common.DomainComponent;
import com.example.payment.service.payment.model.PaymentOrderStatus;
import com.example.payment.service.payment.service.strategy.PaymentStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@DomainComponent
public class PaymentFactory {

    Map<PaymentOrderStatus, PaymentStatus> paymentServiceMap;

    public PaymentFactory(Set<PaymentStatus> paymentStatusSet) {
        buildPaymentServiceMap(paymentStatusSet);
    }

    public PaymentStatus findService(PaymentOrderStatus status){
        return paymentServiceMap.get(status);
    }

    private void buildPaymentServiceMap(Set<PaymentStatus> paymentStatusSet) {
        paymentServiceMap = new HashMap<>();
        paymentStatusSet.forEach(service -> paymentServiceMap.put(service.getName(), service));
    }

}
