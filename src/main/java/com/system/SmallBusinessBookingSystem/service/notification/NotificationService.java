package com.system.SmallBusinessBookingSystem.service.notification;

import com.system.SmallBusinessBookingSystem.service.models.Notification;

public interface NotificationService {
    void sendNotification(Notification notification, String userId, String bookingId);
}