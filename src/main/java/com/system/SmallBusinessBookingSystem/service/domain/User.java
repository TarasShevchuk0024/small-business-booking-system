package com.system.SmallBusinessBookingSystem.service.domain;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
@ToString
public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private UserType type;
    private UserStatus status;
}
