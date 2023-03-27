package com.example.order.service.common.exception;

public class OrderDomainException extends RuntimeException {
    private final String key;
    private final String[] args;

    public OrderDomainException(String key) {
        super(key);
        this.key = key;
        args = new String[0];
    }

    public OrderDomainException(String key, String... args) {
        super(key);
        this.key = key;
        this.args = args;
    }
}
