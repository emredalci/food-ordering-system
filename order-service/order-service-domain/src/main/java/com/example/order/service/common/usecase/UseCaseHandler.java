package com.example.order.service.common.usecase;

public interface UseCaseHandler <R, T extends UseCase>{

    R handler(T useCase);
}
