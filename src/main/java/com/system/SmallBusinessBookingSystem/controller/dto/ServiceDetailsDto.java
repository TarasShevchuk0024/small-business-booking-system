package com.system.SmallBusinessBookingSystem.controller.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ServiceDetailsDto {
    private final String id;
    private final String serviceName;
    private final String description;
    private final Integer durationMinutes;
    private final Double priceEur;
    private final Instant createdAt;
    private final Instant updatedAt;
}