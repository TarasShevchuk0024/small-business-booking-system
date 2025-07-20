package com.system.SmallBusinessBookingSystem.controller.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BookingDetailsDto {
    private final String id;
    private final String userId;
    private final String firstName;
    private final String lastName;
    private final String serviceId;
    private final String serviceName;
    private final Instant dateTime;
    private final String status;
    private final Instant createdAt;
    private final Instant updatedAt;
}