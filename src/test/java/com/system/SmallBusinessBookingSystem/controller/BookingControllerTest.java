package com.system.SmallBusinessBookingSystem.controller;

import com.system.SmallBusinessBookingSystem.controller.dto.BookingCreateDto;
import com.system.SmallBusinessBookingSystem.controller.dto.BookingUpdateDto;
import com.system.SmallBusinessBookingSystem.mapper.BookingMapper;
import com.system.SmallBusinessBookingSystem.repository.ServiceRepository;
import com.system.SmallBusinessBookingSystem.repository.UserRepository;
import com.system.SmallBusinessBookingSystem.service.booking.BookingService;
import com.system.SmallBusinessBookingSystem.service.models.Booking;
import com.system.SmallBusinessBookingSystem.service.models.BookingStatus;
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
class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @Mock
    private BookingMapper bookingMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private BookingController bookingController;

    @Test
    void shouldCreateBooking() {
        // given
        BookingCreateDto dto = BookingCreateDto.builder()
                .userId("user1")
                .serviceId("service1")
                .dateTime(Instant.now())
                .build();

        // when
        Booking booking = Booking.builder().userId("user1").serviceId("service1").build();

        when(bookingMapper.toBooking(dto)).thenReturn(booking);

        ResponseEntity<Void> response = bookingController.createBooking(dto);

        // then
        verify(bookingMapper).toBooking(dto);
        verify(bookingService).createBooking(booking);
        assertEquals(201, response.getStatusCode().value());
    }

    @Test
    void shouldUpdateBookingStatus() {
        // given
        BookingUpdateDto dto = BookingUpdateDto.builder()
                .id("booking1")
                .status(BookingStatus.CONFIRMED.name())
                .build();

        // when
        ResponseEntity<Void> response = bookingController.updateBookingStatus("booking1", dto);

        // then
        verify(bookingService).updateBookingStatus("booking1", BookingStatus.CONFIRMED);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void shouldAcceptBooking() {
        // given
        ResponseEntity<Void> response = bookingController.acceptBooking("booking1");

        // then
        verify(bookingService).acceptBooking("booking1");
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void shouldCancelBooking() {
        // given
        ResponseEntity<Void> response = bookingController.cancelBooking("booking1");

        // then
        verify(bookingService).cancelBooking("booking1");
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void shouldDeleteBooking() {
        // given
        ResponseEntity<Void> response = bookingController.deleteBooking("booking1");

        // then
        verify(bookingService).deleteBooking("booking1");
        assertEquals(204, response.getStatusCode().value());
    }
}