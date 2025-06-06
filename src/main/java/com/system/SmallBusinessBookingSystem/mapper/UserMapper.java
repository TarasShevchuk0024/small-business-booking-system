package com.system.SmallBusinessBookingSystem.mapper;

import com.system.SmallBusinessBookingSystem.controller.dto.UserDetailsDto;
import com.system.SmallBusinessBookingSystem.controller.dto.UserRegistrationDto;
import com.system.SmallBusinessBookingSystem.controller.dto.UserUpdateDto;
import com.system.SmallBusinessBookingSystem.service.domain.User;
import com.system.SmallBusinessBookingSystem.service.domain.UserStatus;
import com.system.SmallBusinessBookingSystem.service.domain.UserType;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {
    public UserDetailsDto toUserDetailsDto(User user) {
        return UserDetailsDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus().name())
                .type(user.getType().name())
                .build();

    }

    public User toUser(UserRegistrationDto userDetailsDto) {
        return User.builder()
                .firstName(userDetailsDto.getFirstName())
                .lastName(userDetailsDto.getLastName())
                .email(userDetailsDto.getEmail())
                .phoneNumber(userDetailsDto.getPhoneNumber())
                .type(UserType.valueOf(userDetailsDto.getType()))
                .build();

    }

    public User toUser(UserUpdateDto userDetailsDto) {
        return User.builder()
                .firstName(userDetailsDto.getFirstName())
                .lastName(userDetailsDto.getLastName())
                .email(userDetailsDto.getEmail())
                .phoneNumber(userDetailsDto.getPhoneNumber())
                .type(UserType.valueOf(userDetailsDto.getType()))
                .status(UserStatus.valueOf(userDetailsDto.getStatus()))
                .build();
    }

}
