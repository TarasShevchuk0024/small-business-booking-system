package com.system.SmallBusinessBookingSystem.service.booking;

import com.system.SmallBusinessBookingSystem.mapper.BookingMapper;
import com.system.SmallBusinessBookingSystem.repository.BookingRepository;
import com.system.SmallBusinessBookingSystem.repository.ServiceRepository;
import com.system.SmallBusinessBookingSystem.repository.UserRepository;
import com.system.SmallBusinessBookingSystem.repository.entity.BookingEntity;
import com.system.SmallBusinessBookingSystem.repository.entity.ServiceEntity;
import com.system.SmallBusinessBookingSystem.repository.entity.UserEntity;
import com.system.SmallBusinessBookingSystem.service.models.Booking;
import com.system.SmallBusinessBookingSystem.service.models.BookingStatus;
import com.system.SmallBusinessBookingSystem.service.models.Notification;
import com.system.SmallBusinessBookingSystem.service.notification.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
        import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookingMapper bookingMapper;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Test
    void shouldCreateBooking() {
        // given
        Booking booking = Booking.builder()
                .userId(UUID.randomUUID().toString())
                .serviceId(UUID.randomUUID().toString())
                .dateTime(Instant.parse("2025-01-01T00:00:00Z"))
                .build();

        ServiceEntity service = new ServiceEntity();
        UserEntity user = new UserEntity();
        BookingEntity entity = mock(BookingEntity.class);

        // when
        when(serviceRepository.findById(any())).thenReturn(Optional.of(service));
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(bookingRepository.existsByServiceIdAndDateTime(any(), any())).thenReturn(false);
        when(bookingMapper.toBookingsEntity(booking)).thenReturn(entity);

        bookingService.createBooking(booking);

        // then
        verify(entity).setService(service);
        verify(entity).setUser(user);
        verify(bookingRepository).save(entity);
    }

    @Test
    void shouldUpdateBookingStatus() {
        // given
        String id = UUID.randomUUID().toString();
        BookingEntity entity = new BookingEntity();

        // when
        when(bookingRepository.findById(any())).thenReturn(Optional.of(entity));

        bookingService.updateBookingStatus(id, BookingStatus.CONFIRMED);

        // then
        assertEquals(BookingStatus.CONFIRMED.name(), entity.getStatus());
        verify(bookingRepository).save(entity);
    }

    @Test
    void shouldDeleteBooking() {
        // given
        UUID id = UUID.randomUUID();
        BookingEntity entity = new BookingEntity();

        // when
        when(bookingRepository.findById(id)).thenReturn(Optional.of(entity));

        // then
        bookingService.deleteBooking(id.toString());

        verify(bookingRepository).delete(entity);
        verify(bookingRepository, never()).deleteById(any());
    }

    @Test
    void shouldSendNotificationOnAccept() {
        // given
        String bookingId = UUID.randomUUID().toString();

        BookingEntity foundEntity = new BookingEntity();

        when(bookingRepository.findById(any())).thenReturn(Optional.of(foundEntity));

        Booking booking = Booking.builder()
                .id(bookingId)
                .userId(UUID.randomUUID().toString())
                .serviceId(UUID.randomUUID().toString())
                .build();

        // getBookingById(id) всередині сервісу робить mapper.toBooking(entity)
        when(bookingMapper.toBooking(foundEntity)).thenReturn(booking);

        // when
        bookingService.acceptBooking(bookingId);

        // then
        verify(notificationService).sendNotification(any(Notification.class), eq(booking.getUserId()), eq(booking.getId()));
        verify(bookingRepository, atLeast(2)).findById(any());
        verify(bookingRepository, atLeastOnce()).save(any(BookingEntity.class));
    }
}
