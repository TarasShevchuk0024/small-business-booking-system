package com.system.SmallBusinessBookingSystem.mapper;

import com.system.SmallBusinessBookingSystem.controller.dto.UserDetailsDto;
import com.system.SmallBusinessBookingSystem.controller.dto.UserUpdateDto;
import com.system.SmallBusinessBookingSystem.service.domain.User;
import com.system.SmallBusinessBookingSystem.service.domain.UserStatus;
import com.system.SmallBusinessBookingSystem.service.domain.UserType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.security.cert.Extension;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    private static UserMapper userMapper;

    @BeforeAll
    static void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    void shouldMapAdminToAdminDetails() {
        // given
        User user = User.builder()
                .id(123)
                .firstName("Taras")
                .lastName("Shevchuk")
                .email("taras.shevchuk0024@gmail.com")
                .phoneNumber("+380634567645")
                .status(UserStatus.ACTIVE)
                .type(UserType.ADMIN)
                .build();

        // when
        UserDetailsDto result = userMapper.toUserDetailsDto(user);

        // then
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getLastName(), result.getLastName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(user.getStatus().name(), result.getStatus());
        assertEquals(user.getType().name(), result.getType());
    }










}


