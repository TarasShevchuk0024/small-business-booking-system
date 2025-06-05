package com.system.SmallBusinessBookingSystem.controller.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserUpdateDto {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;
    private final String password;
    private final String type;
    private final String status;
}
