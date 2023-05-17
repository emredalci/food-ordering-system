package com.example.payment.service.common.rest;


import com.example.payment.service.common.usecase.UseCasePublisherImpl;

import java.util.List;

public class BaseController extends UseCasePublisherImpl {

    protected  <T> Response<DataResponse<T>> respond(List<T> items) {
        return ResponseBuilder.build(items);
    }

    protected  <T> Response<DataResponse<T>> respond(List<T> items, Integer page, Integer size, Long totalSize) {
        return ResponseBuilder.build(items, page, size, totalSize);
    }

    protected <T> Response<T> respond(T item){
        return ResponseBuilder.build(item);
    }

    protected Response<ErrorResponse> respond(ErrorResponse errorResponse){
        return ResponseBuilder.build(errorResponse);
    }

}
