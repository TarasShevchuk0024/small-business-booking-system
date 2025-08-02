package com.system.SmallBusinessBookingSystem.service.notification;

import com.system.SmallBusinessBookingSystem.exception.NotificationAlreadyExistsException;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private NotificationMapper notificationMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Test
    void shouldSendEmailNotification() {
        // given
        String userId = UUID.randomUUID().toString();
        String bookingId = UUID.randomUUID().toString();

        Notification notification = Notification.builder()
                .message("Email Test")
                .type("EMAIL")
                .sentAt(Instant.now())
                .build();

        UserEntity user = UserEntity.builder().email("test@mail.com").build();
        BookingEntity booking = new BookingEntity();
        NotificationEntity entity = mock(NotificationEntity.class);

        // when
        when(notificationRepository.findByUserIdAndBookingId(any(), any())).thenReturn(Optional.empty());
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(bookingRepository.findById(any())).thenReturn(Optional.of(booking));
        when(notificationMapper.toEntity(notification)).thenReturn(entity);

        notificationService.sendNotification(notification, userId, bookingId);

        // then
        verify(notificationRepository).save(entity);
        verify(entity).setUser(user);
        verify(entity).setBooking(booking);
        verify(emailService).sendEmail(eq("test@mail.com"), any(), eq("Email Test"));
    }

    @Test
    void shouldThrowWhenNotificationAlreadyExists() {
        // given
        String userId = UUID.randomUUID().toString();
        String bookingId = UUID.randomUUID().toString();
        Notification notification = Notification.builder().message("Duplicate").build();

        // when
        when(notificationRepository.findByUserIdAndBookingId(any(), any())).thenReturn(Optional.of(new NotificationEntity()));

        // then
        try {
            notificationService.sendNotification(notification, userId, bookingId);
            fail("Expected NotificationAlreadyExistsException");
        } catch (NotificationAlreadyExistsException ignored) {}
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        // given
        String userId = UUID.randomUUID().toString();
        String bookingId = UUID.randomUUID().toString();
        Notification notification = Notification.builder().message("NoUser").build();

        // when
        when(notificationRepository.findByUserIdAndBookingId(any(), any())).thenReturn(Optional.empty());
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        // then
        try {
            notificationService.sendNotification(notification, userId, bookingId);
            fail("Expected UserNotFoundException");
        } catch (UserNotFoundException ignored) {}
    }
}