package com.system.SmallBusinessBookingSystem.mapper;

import com.system.SmallBusinessBookingSystem.controller.dto.NotificationCreateDto;
import com.system.SmallBusinessBookingSystem.repository.entity.NotificationEntity;
import com.system.SmallBusinessBookingSystem.service.models.Notification;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class NotificationMapper {

    public Notification toNotification(NotificationCreateDto dto) {
        return Notification.builder()
                .message(dto.getMessage())
                .sentAt(dto.getSentAt() != null ? dto.getSentAt() : Instant.now())
                .type(dto.getType().name())
                .build();
    }

    public Notification toNotification(NotificationEntity entity) {
        return Notification.builder()
                .id(entity.getId().toString())
                .message(entity.getMessage())
                .sentAt(entity.getSentAt())
                .type(entity.getType())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public NotificationEntity toEntity(Notification notification) {
        NotificationEntity entity = new NotificationEntity();
        if (notification.getId() != null) {
            entity.setId(UUID.fromString(notification.getId()));
        }
        entity.setMessage(notification.getMessage());
        entity.setSentAt(notification.getSentAt());
        entity.setType(notification.getType());
        entity.setCreatedAt(notification.getCreatedAt());
        entity.setUpdatedAt(notification.getUpdatedAt());
        return entity;
    }
}