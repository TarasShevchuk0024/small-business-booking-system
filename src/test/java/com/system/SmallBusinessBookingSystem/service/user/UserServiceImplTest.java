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
    // This is a mock for PasswordEncoder, which is not used in this test but might be used in other methods.
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
        // Since Instant.now() is a static method, we cannot verify it directly.
        // Instead, we can verify that the method was called, but we won't check the exact value.
        verify(user).setCreatedAt(any());
        // Since UUID.randomUUID() is a static method, we cannot verify it directly.
        // Instead, we can verify that the method was called, but we won't check the exact value.
        verify(user).setToken(any());

        verify(userMapper).toUserEntity(user);
        verify(userRepository).save(userEntity);
        // Verify that the emailService.sendEmail method was called with the user's email, a subject, and a body.
        // We use eq() for the email and any() for the subject and body since we don't care about their exact values in this test.
        // The eq() method is used to match the exact value of the email, while any() allows any value for the subject and body.
        verify(emailService).sendEmail(eq(user.getEmail()), any(), any());

        // Rule of Thumb:
        // If you're using only real values or mocks, no need for eq().
        // If you're using any matchers (any(), argThat(), etc.), wrap all other arguments with eq() too.
    }

}