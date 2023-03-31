package com.example.order.service.common.usecase;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
public class UseCaseHandlerRegistry {

    public static final UseCaseHandlerRegistry INSTANCE = new UseCaseHandlerRegistry();

    private final Map<Class<? extends UseCase>, UseCaseHandler<?, ? extends UseCase>> registryForUseCaseHandlers;
    private final Map<Class<? extends UseCase>, VoidUseCaseHandler<? extends UseCase>> registryForVoidUseCaseHandlers;
    private final Map<Class<?>, NoUseCaseHandler<?>> registryForNoUseCaseHandlers;

    private UseCaseHandlerRegistry() {
        registryForUseCaseHandlers = new HashMap<>();
        registryForVoidUseCaseHandlers = new HashMap<>();
        registryForNoUseCaseHandlers = new HashMap<>();
    }

    public <R,T extends UseCase> void register(Class<T> useCase, UseCaseHandler<R,T> useCaseHandler){
        log.info("Use case {} is registered by handler {}", useCase.getSimpleName(), useCaseHandler.getClass().getSimpleName());
        registryForUseCaseHandlers.put(useCase, useCaseHandler);
    }

    public <T extends UseCase> void register(Class<T> useCase, VoidUseCaseHandler<T> useCaseHandler){
        log.info("Use case {} is registered by void handler {}", useCase.getSimpleName(), useCaseHandler.getClass().getSimpleName());
        registryForVoidUseCaseHandlers.put(useCase, useCaseHandler);
    }

    public <R> void register(Class<R> useCase, NoUseCaseHandler<R> useCaseHandler) {
        log.info("Use case {} is registered by no param handler {}", useCase.getSimpleName(), useCaseHandler.getClass().getSimpleName());
        registryForNoUseCaseHandlers.put(useCase, useCaseHandler);
    }

    public UseCaseHandler<?, ? extends UseCase> detectUseCaseHandlerFrom(Class<? extends UseCase> useCaseClass) {
        return registryForUseCaseHandlers.get(useCaseClass);
    }

    public VoidUseCaseHandler<? extends UseCase> detectVoidUseCaseHandlerFrom(Class<? extends UseCase> useCaseClass) {
        return registryForVoidUseCaseHandlers.get(useCaseClass);
    }

    public NoUseCaseHandler<?> detectNoUseCaseHandlerFrom(Class<?> returnClass) {
        return registryForNoUseCaseHandlers.get(returnClass);
    }

}
