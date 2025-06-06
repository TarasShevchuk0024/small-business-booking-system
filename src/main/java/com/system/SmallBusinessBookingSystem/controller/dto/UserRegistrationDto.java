package com.system.SmallBusinessBookingSystem.controller.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserRegistrationDto {
    private final Integer id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;
    private final String type;
}
