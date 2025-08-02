package com.system.SmallBusinessBookingSystem.repository;

import com.system.SmallBusinessBookingSystem.repository.entity.BookingEntity;
import com.system.SmallBusinessBookingSystem.repository.entity.NotificationEntity;
import com.system.SmallBusinessBookingSystem.repository.entity.UserEntity;
import com.system.SmallBusinessBookingSystem.service.models.UserStatus;
import com.system.SmallBusinessBookingSystem.service.models.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotificationRepositoryIT {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    private UUID userId;
    private UUID bookingId;

    @BeforeEach
    void setUp() {
        notificationRepository.deleteAll();
        bookingRepository.deleteAll();
        userRepository.deleteAll();

        UserEntity user = UserEntity.builder()
                .firstName("Taras")
                .lastName("Test")
                .email("notif@test.com")
                .phoneNumber("123456789")
                .password("Test-pass")
                .status(UserStatus.ACTIVE.name())
                .type(UserType.ROLE_ADMIN.name())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        userId = userRepository.save(user).getId();

        BookingEntity booking = BookingEntity.builder()
                .dateTime(Instant.now())
                .status("PENDING")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .user(user)
                .build();

        bookingId = bookingRepository.save(booking).getId();
    }

    @Test
    void shouldSaveAndFindNotification() {
        // given
        NotificationEntity notification = NotificationEntity.builder()
                .message("Test Notification")
                .sentAt(Instant.now())
                .type("EMAIL")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .user(userRepository.findById(userId).orElseThrow())
                .booking(bookingRepository.findById(bookingId).orElseThrow())
                .build();

        // when
        NotificationEntity saved = notificationRepository.save(notification);
        assertNotNull(saved.getId());

        // then
        NotificationEntity found = notificationRepository.findById(saved.getId()).orElseThrow();
        assertEquals("Test Notification", found.getMessage());
        assertEquals("EMAIL", found.getType());
        assertEquals(userId, found.getUser().getId());
        assertEquals(bookingId, found.getBooking().getId());
    }

    @Test
    void shouldFindByUserId() {
        // given
        NotificationEntity notification = NotificationEntity.builder()
                .message("Test message")
                .sentAt(Instant.now())
                .type("SMS")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .user(userRepository.findById(userId).orElseThrow())
                .booking(bookingRepository.findById(bookingId).orElseThrow())
                .build();

        // when
        notificationRepository.save(notification);

        // then
        List<NotificationEntity> list = notificationRepository.findByUserId(userId);
        assertEquals(1, list.size());
    }

    @Test
    void shouldFindByUserIdAndBookingId() {
        // given
        NotificationEntity notification = NotificationEntity.builder()
                .message("Test message")
                .sentAt(Instant.now())
                .type("EMAIL")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .user(userRepository.findById(userId).orElseThrow())
                .booking(bookingRepository.findById(bookingId).orElseThrow())
                .build();

        // when
        notificationRepository.save(notification);

        // then
        assertTrue(notificationRepository.findByUserIdAndBookingId(userId, bookingId).isPresent());
    }
}