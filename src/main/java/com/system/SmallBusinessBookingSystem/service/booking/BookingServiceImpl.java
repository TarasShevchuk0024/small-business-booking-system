package com.system.SmallBusinessBookingSystem.service.booking;

import com.system.SmallBusinessBookingSystem.exception.BookingConflictException;
import com.system.SmallBusinessBookingSystem.exception.BookingNotFoundException;
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
import com.system.SmallBusinessBookingSystem.service.models.NotificationType;
import com.system.SmallBusinessBookingSystem.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final BookingMapper bookingMapper;
    private final NotificationService notificationService;

    @Override
    public void createBooking(Booking booking) {
        log.info("Creating new booking for service: {}, user: {}", booking.getServiceId(), booking.getUserId());

        UUID serviceId = UUID.fromString(booking.getServiceId());
        UUID userId = UUID.fromString(booking.getUserId());

        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new BookingNotFoundException("Service not found"));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new BookingNotFoundException("User not found"));

        if (bookingRepository.existsByServiceIdAndDateTime(serviceId, booking.getDateTime())) {
            throw new BookingConflictException("Time slot is already booked for this service");
        }
        booking.setStatus(BookingStatus.PENDING.name());
        booking.setCreatedAt(Instant.now());
        booking.setUpdatedAt(Instant.now());

        BookingEntity entity = bookingMapper.toBookingsEntity(booking);
        entity.setService(service);
        entity.setUser(user);

        bookingRepository.save(entity);

        log.info("Booking created for service: {}", serviceId);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(bookingMapper::toBooking)
                .toList();
    }

    @Override
    public Booking getBookingById(String id) {
        UUID bookingId = UUID.fromString(id);

        BookingEntity entity = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking with id " + id + " not found"));

        return bookingMapper.toBooking(entity);
    }

    @Override
    public void updateBookingStatus(String id, BookingStatus status) {
        UUID bookingId = UUID.fromString(id);

        BookingEntity entity = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking with id " + id + " not found"));

        entity.setStatus(status.name());
        entity.setUpdatedAt(Instant.now());

        bookingRepository.save(entity);
    }

    @Override
    public void acceptBooking(String id) {
        updateBookingStatus(id, BookingStatus.CONFIRMED);
        Booking booking = getBookingById(id);

        Notification notification = Notification.builder()
                .message("Your booking has been confirmed!")
                .sentAt(Instant.now())
                .type(NotificationType.EMAIL.name())
                .build();

        notificationService.sendNotification(notification, booking.getUserId(), booking.getId());
    }

    @Override
    public void cancelBooking(String id) {
        updateBookingStatus(id, BookingStatus.CANCELLED);
        Booking booking = getBookingById(id);

        Notification notification = Notification.builder()
                .message("Your booking has been cancelled.")
                .sentAt(Instant.now())
                .type(NotificationType.EMAIL.name())
                .build();

        notificationService.sendNotification(notification, booking.getUserId(), booking.getId());
    }

    @Override
    @Transactional
    public void deleteBooking(String id) {
        UUID bookingId = UUID.fromString(id);

        BookingEntity entity = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking with id " + id + " not found"));

        // Видаляємо саме сутність, щоб JPA спрацював каскадом і orphanRemoval
        bookingRepository.delete(entity);
        log.info("Booking {} deleted", bookingId);
    }
}