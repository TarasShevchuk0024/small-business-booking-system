package com.system.SmallBusinessBookingSystem.repository;

import com.system.SmallBusinessBookingSystem.repository.entity.UserEntity;
import com.system.SmallBusinessBookingSystem.service.models.UserStatus;
import com.system.SmallBusinessBookingSystem.service.models.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


// IT is Integration Test
// This class is intended to test the AdminRepository functionality in an integration context.

@SpringBootTest
class UserRepositoryIT {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        // Clean up the repository before each test
        userRepository.deleteAll();
    }

    @Test
    void shouldSuccessfullySaveUser() {
        // given
        UserEntity user = UserEntity.builder()
                .firstName("Taras")
                .lastName("Shevchuk")
                .email("taras.shevchuk0024@gmail.com")
                .phoneNumber("+380633570183")
                .password("Taras1234")
                .status(UserStatus.ACTIVE.name())
                .type(UserType.ROLE_ADMIN.getValue())
                .createdAt(Instant.now().minusSeconds(60))
                .updatedAt(Instant.now())
                .token(UUID.randomUUID())
                .build();

        // when
        userRepository.save(user);

        // then
        UserEntity savedUser = userRepository.findByEmailIgnoreCase(user.getEmail()).orElseThrow();

        // ID is generated automatically, so we don't check it here
        assertEquals(user.getFirstName(), savedUser.getFirstName());
        assertEquals(user.getLastName(), savedUser.getLastName());
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getPhoneNumber(), savedUser.getPhoneNumber());
        assertEquals(user.getPassword(), savedUser.getPassword());
        assertEquals(user.getStatus(), savedUser.getStatus());
        assertEquals(user.getType(), savedUser.getType());
        assertEquals(user.getCreatedAt(), savedUser.getCreatedAt());
        assertEquals(user.getUpdatedAt(), savedUser.getUpdatedAt());
        assertEquals(user.getToken(), savedUser.getToken());
    }

}