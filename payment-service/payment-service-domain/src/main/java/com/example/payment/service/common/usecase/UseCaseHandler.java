package com.example.payment.service.common.usecase;

public interface UseCaseHandler <R, T extends UseCase>{

    R handler(T useCase);
}
