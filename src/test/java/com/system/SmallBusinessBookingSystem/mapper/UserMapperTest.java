package com.system.SmallBusinessBookingSystem.mapper;

import com.system.SmallBusinessBookingSystem.controller.dto.UserRegistrationDto;
import com.system.SmallBusinessBookingSystem.repository.entity.UserEntity;
import com.system.SmallBusinessBookingSystem.service.models.User;
import com.system.SmallBusinessBookingSystem.service.models.UserStatus;
import com.system.SmallBusinessBookingSystem.service.models.UserType;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserMapperTest {
    private final UserMapper userMapper = new UserMapper();

    @Test
    void shouldMapUserRegistrationDtoToUser() {
        // given
        UserType userType = UserType.ROLE_USER;
        UserRegistrationDto dto = UserRegistrationDto.builder()
                .firstName("Taras")
                .lastName("Shevchuk")
                .email("taras.shevchuk0024@gmail.com")
                .phoneNumber("380633570183")
                .type(userType.getValue())
                .build();

        // when
        User user = userMapper.toUser(dto);

        // then
        assertNull(user.getId());
        assertEquals(dto.getFirstName(), user.getFirstName());
        assertEquals(dto.getLastName(), user.getLastName());
        assertEquals(dto.getEmail(), user.getEmail());
        assertEquals(dto.getPhoneNumber(), user.getPhoneNumber());
        assertEquals(userType, user.getType());
    }

    @Test
    void shouldMapUserToUserEntity() {
        // given
        User user = User.builder()
                .id(UUID.randomUUID())
                .firstName("Anna")
                .lastName("Shevchuk")
                .email("anna.shevchuk@gmail.com")
                .phoneNumber("380634568456")
                .status(UserStatus.ACTIVE)
                .type(UserType.ROLE_USER)
                .createdAt(Instant.now().minusSeconds(60))
                .updatedAt(Instant.now())
                .token(UUID.randomUUID())
                .build();

        // when
        UserEntity entity = userMapper.toUserEntity(user);

        // then
        assertEquals(user.getId(), entity.getId());
        assertEquals(user.getFirstName(), entity.getFirstName());
        assertEquals(user.getLastName(), entity.getLastName());
        assertEquals(user.getEmail(), entity.getEmail());
        assertEquals(user.getPhoneNumber(), entity.getPhoneNumber());
        assertEquals(user.getStatus().name(), entity.getStatus());
        assertEquals(user.getType().name(), entity.getType());
        assertEquals(user.getCreatedAt(), entity.getCreatedAt());
        assertEquals(user.getUpdatedAt(), entity.getUpdatedAt());
        assertEquals(user.getToken(), entity.getToken());
    }
}