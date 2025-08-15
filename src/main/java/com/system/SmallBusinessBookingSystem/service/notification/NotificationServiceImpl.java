package com.system.SmallBusinessBookingSystem.service.notification;

import com.system.SmallBusinessBookingSystem.exception.UserNotFoundException;
import com.system.SmallBusinessBookingSystem.mapper.NotificationMapper;
import com.system.SmallBusinessBookingSystem.repository.BookingRepository;
import com.system.SmallBusinessBookingSystem.repository.NotificationRepository;
import com.system.SmallBusinessBookingSystem.repository.UserRepository;
import com.system.SmallBusinessBookingSystem.repository.entity.BookingEntity;
import com.system.SmallBusinessBookingSystem.repository.entity.NotificationEntity;
import com.system.SmallBusinessBookingSystem.repository.entity.UserEntity;
import com.system.SmallBusinessBookingSystem.service.email.EmailService;
import com.system.SmallBusinessBookingSystem.service.models.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final EmailService emailService;

    @Override
    public void sendNotification(Notification notification, String userId, String bookingId) {
        log.info("Sending notification to user: {}", userId);

        UUID userUUID = UUID.fromString(userId);
        UUID bookingUUID = UUID.fromString(bookingId);

        UserEntity user = userRepository.findById(userUUID)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        BookingEntity booking = bookingRepository.findById(bookingUUID)
                .orElse(null);

        notification.setCreatedAt(Instant.now());
        notification.setUpdatedAt(Instant.now());

        NotificationEntity entity = notificationMapper.toEntity(notification);
        entity.setUser(user);
        entity.setBooking(booking);

        notificationRepository.save(entity);

        if ("EMAIL".equalsIgnoreCase(notification.getType())) {
            emailService.sendEmail(
                    user.getEmail(),
                    "Notification",
                    notification.getMessage()
            );
        }

        log.info("Notification saved and sent to {}", user.getEmail());
    }
}