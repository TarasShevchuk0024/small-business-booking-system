package com.system.SmallBusinessBookingSystem.controller;

import com.system.SmallBusinessBookingSystem.controller.dto.UserRegistrationDto;
import com.system.SmallBusinessBookingSystem.mapper.UserMapper;
import com.system.SmallBusinessBookingSystem.service.models.User;
import com.system.SmallBusinessBookingSystem.service.user.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    UserService userService;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserController userController;

    @Test
    void shouldSuccessfullyCreateUser() {

        // given
        UserRegistrationDto userRegistrationDto = mock(UserRegistrationDto.class);
        User user = mock(User.class);

        // when
        when(userMapper.toUser(userRegistrationDto)).thenReturn(user);

        // when
        userController.createUser(userRegistrationDto);

        // then
        verify(userMapper).toUser(userRegistrationDto);
        verify(userService).createUser(user);
    }
}