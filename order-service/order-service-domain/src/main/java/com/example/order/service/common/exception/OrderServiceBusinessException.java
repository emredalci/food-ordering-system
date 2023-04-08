package com.example.order.service.common.exception;

public class OrderServiceBusinessException extends RuntimeException {
    private final String key;
    private final String[] args;

    public OrderServiceBusinessException(String key) {
        super(key);
        this.key = key;
        args = new String[0];
    }

    public OrderServiceBusinessException(String key, String... args) {
        super(key);
        this.key = key;
        this.args = args;
    }
}
