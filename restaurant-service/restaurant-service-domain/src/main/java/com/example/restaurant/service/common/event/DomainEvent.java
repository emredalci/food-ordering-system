package com.example.restaurant.service.common.event;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@SuperBuilder
public abstract class DomainEvent {

    @Builder.Default
    private String traceId = UUID.randomUUID().toString();

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private List<String> failureMessages = new ArrayList<>();

}
