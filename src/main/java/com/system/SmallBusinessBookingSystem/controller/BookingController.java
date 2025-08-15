package com.system.SmallBusinessBookingSystem.controller;

import com.system.SmallBusinessBookingSystem.controller.dto.BookingCreateDto;
import com.system.SmallBusinessBookingSystem.controller.dto.BookingDetailsDto;
import com.system.SmallBusinessBookingSystem.controller.dto.BookingUpdateDto;
import com.system.SmallBusinessBookingSystem.mapper.BookingMapper;
import com.system.SmallBusinessBookingSystem.repository.ServiceRepository;
import com.system.SmallBusinessBookingSystem.repository.UserRepository;
import com.system.SmallBusinessBookingSystem.repository.entity.ServiceEntity;
import com.system.SmallBusinessBookingSystem.repository.entity.UserEntity;
import com.system.SmallBusinessBookingSystem.service.booking.BookingService;
import com.system.SmallBusinessBookingSystem.service.models.Booking;
import com.system.SmallBusinessBookingSystem.service.models.BookingStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final BookingMapper bookingMapper;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;

    @PostMapping
    public ResponseEntity<Void> createBooking(@RequestBody BookingCreateDto dto) {
        Booking booking = bookingMapper.toBooking(dto);
        bookingService.createBooking(booking);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BookingDetailsDto>> getAllBookings() {
        List<BookingDetailsDto> bookings = bookingService.getAllBookings()
                .stream()
                .map(booking -> {
                    String firstName = userRepository.findById(UUID.fromString(booking.getUserId()))
                            .map(UserEntity::getFirstName)
                            .orElse("Unknown");
                    String lastName = userRepository.findById(UUID.fromString(booking.getUserId()))
                            .map(UserEntity::getLastName)
                            .orElse("User");

                    String serviceName = serviceRepository.findById(UUID.fromString(booking.getServiceId()))
                            .map(ServiceEntity::getServiceName)
                            .orElse("Unknown");

                    return bookingMapper.toBookingDetailsDto(booking, firstName, lastName, serviceName);
                })
                .toList();

        if (bookings.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDetailsDto> getBookingById(@PathVariable String id) {
        Booking booking = bookingService.getBookingById(id);

        String firstName = userRepository.findById(UUID.fromString(booking.getUserId()))
                .map(UserEntity::getFirstName)
                .orElse("Unknown");
        String lastName = userRepository.findById(UUID.fromString(booking.getUserId()))
                .map(UserEntity::getLastName)
                .orElse("User");

        String serviceName = serviceRepository.findById(UUID.fromString(booking.getServiceId()))
                .map(ServiceEntity::getServiceName)
                .orElse("Unknown");

        BookingDetailsDto dto = bookingMapper.toBookingDetailsDto(booking, firstName, lastName, serviceName);

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBookingStatus(
            @PathVariable String id,
            @RequestBody BookingUpdateDto dto
    ) {
        if (dto == null || dto.getStatus() == null) {
            return ResponseEntity.badRequest().build();
        }

        final BookingStatus newStatus;
        try {
            newStatus = BookingStatus.valueOf(dto.getStatus());
        } catch (IllegalArgumentException ex) {
            // передали щось відмінне від PENDING/CONFIRMED/CANCELLED
            return ResponseEntity.badRequest().build();
        }

        // важливо: ці методи вже шлють email через Notification/EmailService
        if (newStatus == BookingStatus.CONFIRMED) {
            bookingService.acceptBooking(id);
        } else if (newStatus == BookingStatus.CANCELLED) {
            bookingService.cancelBooking(id);
        } else {
            bookingService.updateBookingStatus(id, newStatus);
        }

        return ResponseEntity.ok().build();
    }


    @PutMapping("/{id}/accept")
    public ResponseEntity<Void> acceptBooking(@PathVariable String id) {
        bookingService.acceptBooking(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable String id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable String id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}