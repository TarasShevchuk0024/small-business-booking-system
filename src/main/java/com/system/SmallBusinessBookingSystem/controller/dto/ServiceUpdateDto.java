package com.system.SmallBusinessBookingSystem.controller.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ServiceUpdateDto {
    private final String id;
    private final String serviceName;
    private final String description;
    private final Integer durationMinutes;
    private final Double priceEur;
}
