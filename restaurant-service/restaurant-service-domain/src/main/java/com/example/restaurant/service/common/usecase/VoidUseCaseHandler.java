package com.example.restaurant.service.common.usecase;

public interface VoidUseCaseHandler<T extends UseCase> {

    void handle(T useCase);
}
