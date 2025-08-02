package com.system.SmallBusinessBookingSystem.controller;

import com.system.SmallBusinessBookingSystem.controller.dto.NotificationCreateDto;
import com.system.SmallBusinessBookingSystem.mapper.NotificationMapper;
import com.system.SmallBusinessBookingSystem.service.models.Notification;
import com.system.SmallBusinessBookingSystem.service.models.NotificationType;
import com.system.SmallBusinessBookingSystem.service.notification.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @Mock
    private NotificationMapper notificationMapper;

    @InjectMocks
    private NotificationController notificationController;

    @Test
    void shouldSendNotification() {
        // given
        String userId = "user1";
        String bookingId = "business1";
        NotificationCreateDto dto = NotificationCreateDto.builder()
                .message("Test message")
                .sentAt(Instant.now())
                .type(NotificationType.EMAIL)
                .build();

        Notification notification = Notification.builder()
                .message("Test message")
                .type(NotificationType.EMAIL.name())
                .build();

        // when
        when(notificationMapper.toNotification(dto)).thenReturn(notification);

        ResponseEntity<Void> response = notificationController.sendNotification(userId, bookingId, dto);

        // then
        verify(notificationMapper).toNotification(dto);
        verify(notificationService).sendNotification(notification, userId, bookingId);
        assertEquals(201, response.getStatusCode().value());
    }
}