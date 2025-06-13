package com.system.SmallBusinessBookingSystem.controller.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BusinessDetailsDto {
    private final String id;
    private final String businessName;
    private final String description;
    private final Instant createdAt;
    private final Instant updatedAt;
}
