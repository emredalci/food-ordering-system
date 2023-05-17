package com.example.payment.service.common.exception;

public class PaymentServiceBusinessException extends RuntimeException{

    private final String key;
    private final String[] args;

    public PaymentServiceBusinessException(String key) {
        super(key);
        this.key = key;
        args = new String[0];
    }

    public PaymentServiceBusinessException(String key, String... args) {
        super(key);
        this.key = key;
        this.args = args;
    }
}
