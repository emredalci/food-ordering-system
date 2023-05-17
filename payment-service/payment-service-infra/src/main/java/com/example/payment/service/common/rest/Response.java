package com.example.payment.service.common.rest;


public class Response<T> {

    private T data;

    private ErrorResponse errors;

    public Response(T data) {
        this.data = data;
    }

    public Response(ErrorResponse errors) {
        this.errors = errors;
    }

    public Response() {
    }

    public T getData() {
        return data;
    }

    public ErrorResponse getErrors() {
        return errors;
    }
}
