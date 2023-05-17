package com.example.payment.service.common.usecase;

public interface VoidUseCaseHandler<T extends UseCase> {

    void handle(T useCase);
}
