package com.example.resturant.service.common.rest;

import java.util.List;

public class DataResponse<T> {

    private List<T> items = List.of();

    private Integer page;

    private Integer size;

    private Long totalSize;

    public DataResponse() {
    }

    public DataResponse(List<T> items) {
        this.items = items;
    }

    public DataResponse(List<T> items, Integer page, Integer size, Long totalSize) {
        this.items = items;
        this.page = page;
        this.size = size;
        this.totalSize = totalSize;
    }

    public List<T> getItems() {
        return items;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getSize() {
        return size;
    }

    public Long getTotalSize() {
        return totalSize;
    }
}
