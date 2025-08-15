package com.system.SmallBusinessBookingSystem.service.user;

import com.system.SmallBusinessBookingSystem.mapper.UserMapper;
import com.system.SmallBusinessBookingSystem.repository.UserRepository;
import com.system.SmallBusinessBookingSystem.repository.entity.UserEntity;
import com.system.SmallBusinessBookingSystem.service.email.EmailService;
import com.system.SmallBusinessBookingSystem.service.models.User;
import com.system.SmallBusinessBookingSystem.service.models.UserStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Mock
    EmailService emailService;

    @SuppressWarnings("unused")
    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void shouldSuccessfullyCreateUser() {
        // given
        User user = mock(User.class);
        UserEntity userEntity = mock(UserEntity.class);

        doReturn(userEntity).when(userMapper).toUserEntity(user);
        doReturn(userEntity).when(userRepository).save(userEntity);
        doNothing().when(emailService).sendEmail(any(), any(), any());

        // when
        userService.createUser(user);

        // then
        verify(user).setStatus(UserStatus.PENDING);
        verify(user).setCreatedAt(any());
        verify(user).setToken(any());

        verify(userMapper).toUserEntity(user);
        verify(userRepository).save(userEntity);
        verify(emailService).sendEmail(eq(user.getEmail()), any(), any());
    }
}