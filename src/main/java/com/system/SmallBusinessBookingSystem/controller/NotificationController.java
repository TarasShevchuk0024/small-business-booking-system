package com.system.SmallBusinessBookingSystem.controller;

import com.system.SmallBusinessBookingSystem.controller.dto.NotificationCreateDto;
import com.system.SmallBusinessBookingSystem.mapper.NotificationMapper;
import com.system.SmallBusinessBookingSystem.service.models.Notification;
import com.system.SmallBusinessBookingSystem.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;

    @PostMapping
    public ResponseEntity<Void> sendNotification(
            @RequestParam String userId,
            @RequestParam String bookingId,
            @RequestBody NotificationCreateDto dto
    ) {
        Notification notification = notificationMapper.toNotification(dto);
        notificationService.sendNotification(notification, userId, bookingId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
