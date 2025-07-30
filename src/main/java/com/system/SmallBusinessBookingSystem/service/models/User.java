package com.system.SmallBusinessBookingSystem.service.models;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@Builder
@ToString
public class User {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private UserType type;
    private UserStatus status;
    private Instant createdAt;
    private Instant updatedAt;
    private UUID token;
}