package com.system.SmallBusinessBookingSystem.mapper;

import com.system.SmallBusinessBookingSystem.controller.dto.UserDetailsDto;
import com.system.SmallBusinessBookingSystem.controller.dto.UserRegistrationDto;
import com.system.SmallBusinessBookingSystem.controller.dto.UserUpdateDto;
import com.system.SmallBusinessBookingSystem.repository.entity.UserEntity;
import com.system.SmallBusinessBookingSystem.service.models.User;
import com.system.SmallBusinessBookingSystem.service.models.UserStatus;
import com.system.SmallBusinessBookingSystem.service.models.UserType;
import org.springframework.stereotype.Component;

import java.util.UUID;


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
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();

    }

    public User toUser(UserRegistrationDto userRegistrationDto) {
        return User.builder()
                .firstName(userRegistrationDto.getFirstName())
                .lastName(userRegistrationDto.getLastName())
                .email(userRegistrationDto.getEmail())
                .phoneNumber(userRegistrationDto.getPhoneNumber())
                .password(userRegistrationDto.getPassword())
                .type(UserType.fromValue(userRegistrationDto.getType()))
                .build();

    }

    public User toUser(UserUpdateDto userDetailsDto) {
        return User.builder()
                .id(userDetailsDto.getId())
                .firstName(userDetailsDto.getFirstName())
                .lastName(userDetailsDto.getLastName())
                .email(userDetailsDto.getEmail())
                .phoneNumber(userDetailsDto.getPhoneNumber())
                .type(UserType.fromValue(userDetailsDto.getType()))
                .status(UserStatus.valueOf(userDetailsDto.getStatus()))
                .build();
    }

    public User toUser(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .phoneNumber(userEntity.getPhoneNumber())
                .type(UserType.fromValue(userEntity.getType()))
                .status(UserStatus.valueOf(userEntity.getStatus()))
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt())
                .password(userEntity.getPassword())
                .build();
    }

    public UserEntity toUserEntity(User user) {
        return UserEntity.builder()
                .id(user.getId() != null ? user.getId() : null)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus().name())
                .type(user.getType().name())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .token(user.getToken())
                .password(user.getPassword())
                .build();
    }
}