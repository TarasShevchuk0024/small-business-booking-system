package com.system.SmallBusinessBookingSystem.mapper;

import com.system.SmallBusinessBookingSystem.controller.dto.NotificationCreateDto;
import com.system.SmallBusinessBookingSystem.repository.entity.NotificationEntity;
import com.system.SmallBusinessBookingSystem.service.models.Notification;
import com.system.SmallBusinessBookingSystem.service.models.NotificationType;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotificationMapperTest {

    private final NotificationMapper notificationMapper = new NotificationMapper();

    @Test
    void shouldMapCreateDtoToNotification() {
        // given
        NotificationCreateDto dto = NotificationCreateDto.builder()
                .message("Test message")
                .sentAt(Instant.now())
                .type(NotificationType.SMS)
                .build();

        // when
        Notification notification = notificationMapper.toNotification(dto);

        // then
        assertEquals(dto.getMessage(), notification.getMessage());
        assertEquals(dto.getSentAt(), notification.getSentAt());
        assertEquals(NotificationType.SMS.name(), notification.getType());
    }

    @Test
    void shouldMapEntityToNotification() {
        // given
        NotificationEntity entity = NotificationEntity.builder()
                .id(UUID.randomUUID())
                .message("Test message")
                .sentAt(Instant.now())
                .type("EMAIL")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        // when
        Notification notification = notificationMapper.toNotification(entity);

        // then
        assertEquals(entity.getMessage(), notification.getMessage());
        assertEquals(entity.getType(), notification.getType());
        assertEquals(entity.getId().toString(), notification.getId());
    }

    @Test
    void shouldMapNotificationToEntity() {
        // given
        Notification notification = Notification.builder()
                .id(UUID.randomUUID().toString())
                .message("Test message")
                .sentAt(Instant.now())
                .type("EMAIL")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        // when
        NotificationEntity entity = notificationMapper.toEntity(notification);

        // then
        assertEquals(notification.getMessage(), entity.getMessage());
        assertEquals(notification.getType(), entity.getType());
        assertEquals(notification.getSentAt(), entity.getSentAt());
    }
}