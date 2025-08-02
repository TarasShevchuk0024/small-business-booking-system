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
                .dateTime(Instant.now())
                .build();

        ServiceEntity service = new ServiceEntity();
        UserEntity user = new UserEntity();
        BookingEntity entity = mock(BookingEntity.class);

        // when
        when(serviceRepository.findById(any())).thenReturn(Optional.of(service));
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
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

        // then
        bookingService.updateBookingStatus(id, BookingStatus.CONFIRMED);

        assertEquals(BookingStatus.CONFIRMED.name(), entity.getStatus());
        verify(bookingRepository).save(entity);
    }

    @Test
    void shouldDeleteBooking() {
        // when
        UUID id = UUID.randomUUID();
        when(bookingRepository.existsById(id)).thenReturn(true);

        // then
        bookingService.deleteBooking(id.toString());

        verify(bookingRepository).deleteById(id);
    }

    @Test
    void shouldSendNotificationOnAccept() {
        // given
        String id = UUID.randomUUID().toString();
        Booking booking = Booking.builder()
                .id(id)
                .userId(UUID.randomUUID().toString())
                .serviceId(UUID.randomUUID().toString())
                .build();

        // when
        when(bookingRepository.findById(any())).thenReturn(Optional.of(new BookingEntity()));
        when(bookingService.getBookingById(id)).thenReturn(booking); // використовуємо spy/partial stub

        // then
        bookingService.acceptBooking(id);

        verify(notificationService).sendNotification(any(Notification.class), eq(booking.getUserId()), eq(booking.getId()));
    }
}