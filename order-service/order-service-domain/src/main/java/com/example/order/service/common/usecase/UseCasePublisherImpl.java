package com.example.order.service.common.usecase;

import com.example.order.service.common.DomainComponent;
import com.example.order.service.common.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@DomainComponent
public class UseCasePublisherImpl implements UseCasePublisher{

    public static final String ERROR_KEY = "order.service.domain.usecase.handler.not.detected";
    @Override
    public <R, T extends UseCase> R publish(Class<R> returnClass, T useCase) {
        UseCaseHandler<R, T> useCaseHandler = (UseCaseHandler<R, T>) UseCaseHandlerRegistry.INSTANCE.detectUseCaseHandlerFrom(useCase.getClass());
        validateUseCaseHandlerDetection(useCase, useCaseHandler);
        return useCaseHandler.handler(useCase);
    }

    @Override
    public <T extends UseCase> void publish(T useCase) {
        VoidUseCaseHandler<T> voidUseCaseHandler = (VoidUseCaseHandler<T>) UseCaseHandlerRegistry.INSTANCE.detectVoidUseCaseHandlerFrom(useCase.getClass());
        validateVoidUseCaseHandlerDetection(useCase, voidUseCaseHandler);

    }

    @Override
    public <R> R publish(Class<R> returnClass) {
        NoUseCaseHandler<R> noUseCaseHandler = (NoUseCaseHandler<R>) UseCaseHandlerRegistry.INSTANCE.detectNoUseCaseHandlerFrom(returnClass.getClass());
        validateNoUseCaseHandlerDetection(noUseCaseHandler);
        return noUseCaseHandler.handle();
    }

    private <T extends UseCase, R> void validateUseCaseHandlerDetection(T useCase, UseCaseHandler<R, T> useCaseHandler) {
        if (Objects.isNull(useCaseHandler)){
            log.error("Use case handler cannot be detected for the use case: {}, handlers: {}", useCase, UseCaseHandlerRegistry.INSTANCE.getRegistryForUseCaseHandlers());
            throw new OrderDomainException(ERROR_KEY);
        }
    }

    private <T extends UseCase> void validateVoidUseCaseHandlerDetection(T useCase, VoidUseCaseHandler<T> voidUseCaseHandler) {
        if (Objects.isNull(voidUseCaseHandler)){
            log.error("Void use case handler cannot be detected for the use case: {}, handlers: {}", useCase, UseCaseHandlerRegistry.INSTANCE.getRegistryForVoidUseCaseHandlers());
            throw new OrderDomainException(ERROR_KEY);
        }
    }

    private <R> void validateNoUseCaseHandlerDetection(NoUseCaseHandler<R> noUseCaseHandler) {
        if (Objects.isNull(noUseCaseHandler)){
            log.error("Void use case handler cannot be detected for the handlers: {}", UseCaseHandlerRegistry.INSTANCE.getRegistryForNoUseCaseHandlers());
            throw new OrderDomainException(ERROR_KEY);
        }
    }

}
