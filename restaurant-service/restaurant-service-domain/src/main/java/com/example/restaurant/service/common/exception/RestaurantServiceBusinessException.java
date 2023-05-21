package com.example.restaurant.service.common.exception;

public class RestaurantServiceBusinessException extends RuntimeException{

    private final String key;
    private final String[] args;

    public RestaurantServiceBusinessException(String key) {
        super(key);
        this.key = key;
        args = new String[0];
    }

    public RestaurantServiceBusinessException(String key, String... args) {
        super(key);
        this.key = key;
        this.args = args;
    }
}
