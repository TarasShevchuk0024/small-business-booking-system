package com.system.SmallBusinessBookingSystem.service.domain;

import lombok.*;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@Builder
@ToString
public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private UserType type;
    private UserStatus status;
    private Instant createdAt;
    private Instant updatedAt;
}
