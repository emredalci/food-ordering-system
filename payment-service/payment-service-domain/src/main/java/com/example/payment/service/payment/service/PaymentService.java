package com.example.payment.service.payment.service;

import com.example.payment.service.common.DomainComponent;
import com.example.payment.service.common.model.Money;
import com.example.payment.service.payment.event.PaymentCancelledEvent;
import com.example.payment.service.payment.event.PaymentCompletedEvent;
import com.example.payment.service.payment.event.PaymentEvent;
import com.example.payment.service.payment.event.PaymentFailedEvent;
import com.example.payment.service.payment.model.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@DomainComponent
@Slf4j
public class PaymentService {

    public PaymentEvent validateAndInitiatePayment(Payment payment, CreditEntry creditEntry, List<CreditHistory> creditHistories) {
        List<String> failureMessages = new ArrayList<>(1);
        payment.validatePayment(failureMessages);
        payment.initializePayment();
        validateCreditEntry(payment.getPrice(), creditEntry, failureMessages);
        creditEntry.subtractCreditAmount(payment.getPrice());
        creditHistories.add(buildCreditHistory(payment, TransactionType.DEBIT));
        validateCreditHistory(creditEntry, creditHistories, failureMessages);
        if (failureMessages.isEmpty()) {
            payment.updateStatus(PaymentStatus.COMPLETED);
            return PaymentCompletedEvent.of(payment);
        }
        payment.updateStatus(PaymentStatus.FAILED);
        return PaymentFailedEvent.of(payment);
    }

    public PaymentEvent validateAndCancelPayment(Payment payment, CreditEntry creditEntry, List<CreditHistory> creditHistories) {
        List<String> failureMessages = new ArrayList<>(1);
        payment.validatePayment(failureMessages);
        creditEntry.addCreditAmount(payment.getPrice());
        creditHistories.add(buildCreditHistory(payment,TransactionType.CREDIT));
        if (failureMessages.isEmpty()){
            payment.updateStatus(PaymentStatus.CANCELLED);
            return PaymentCancelledEvent.of(payment);
        }
        payment.updateStatus(PaymentStatus.FAILED);
        return PaymentFailedEvent.of(payment);
    }

    private void validateCreditHistory(CreditEntry creditEntry, List<CreditHistory> creditHistories, List<String> failureMessages) {
        Money totalCreditHistory = getTotalHistoryAmount(creditHistories, TransactionType.CREDIT);
        Money totalDebitHistory = getTotalHistoryAmount(creditHistories, TransactionType.DEBIT);
        if (totalDebitHistory.isGreaterThan(totalCreditHistory)) {
            failureMessages.add(String.format("Customer id %s does not have enough credit according to credit history!",
                    creditEntry.getCustomerId().toString()));
        }
        if (Boolean.FALSE.equals(creditEntry.getTotalCreditAmount().equals(totalCreditHistory.subtract(totalDebitHistory)))) {
            failureMessages.add(String.format("Credit history total is not equal to current credit for customer id: %s",
                    creditEntry.getCustomerId().toString()));
        }
    }

    private Money getTotalHistoryAmount(List<CreditHistory> creditHistories, TransactionType transactionType) {
        return creditHistories.stream()
                .filter(creditHistory -> transactionType.equals(creditHistory.getTransactionType()))
                .map(CreditHistory::getAmount)
                .reduce(Money.ZERO, Money::add);
    }

    private CreditHistory buildCreditHistory(Payment payment, TransactionType transactionType) {
        return CreditHistory.builder()
                .id(UUID.randomUUID())
                .customerId(payment.getCustomerId())
                .amount(payment.getPrice())
                .transactionType(transactionType)
                .build();
    }

    private void validateCreditEntry(Money price, CreditEntry creditEntry, List<String> failureMessages) {
        if (price.isGreaterThan(creditEntry.getTotalCreditAmount())) {
            failureMessages.add(String.format("Customer id = %s does not have enough credit for payment", creditEntry.getCustomerId().toString()));
        }
    }
}
