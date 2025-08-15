package com.system.SmallBusinessBookingSystem.mapper;

import com.system.SmallBusinessBookingSystem.controller.dto.BookingCreateDto;
import com.system.SmallBusinessBookingSystem.controller.dto.BookingDetailsDto;
import com.system.SmallBusinessBookingSystem.controller.dto.BookingUpdateDto;
import com.system.SmallBusinessBookingSystem.repository.entity.BookingEntity;
import com.system.SmallBusinessBookingSystem.repository.entity.ServiceEntity;
import com.system.SmallBusinessBookingSystem.repository.entity.UserEntity;
import com.system.SmallBusinessBookingSystem.service.models.Booking;
import com.system.SmallBusinessBookingSystem.service.models.BookingStatus;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookingMapperTest {

    private final BookingMapper bookingMapper = new BookingMapper();

    @Test
    void shouldMapCreateDtoToBooking() {
        // given
        BookingCreateDto dto = BookingCreateDto.builder()
                .userId("user1")
                .serviceId("service1")
                .dateTime(Instant.now())
                .build();

        // when
        Booking booking = bookingMapper.toBooking(dto);

        // then
        assertEquals(dto.getUserId(), booking.getUserId());
        assertEquals(dto.getServiceId(), booking.getServiceId());
        assertEquals(BookingStatus.PENDING.name(), booking.getStatus());
    }

    @Test
    void shouldMapUpdateDtoToBooking() {
        // given
        BookingUpdateDto dto = BookingUpdateDto.builder()
                .id("booking1")
                .userId("user1")
                .serviceId("service1")
                .status(BookingStatus.CONFIRMED.name())
                .build();

        // when
        Booking booking = bookingMapper.toBooking(dto);

        // then
        assertEquals(dto.getId(), booking.getId());
        assertEquals(dto.getStatus(), booking.getStatus());
    }

    @Test
    void shouldMapEntityToBooking() {
        // given
        BookingEntity entity = BookingEntity.builder()
                .id(UUID.randomUUID())
                .dateTime(Instant.now())
                .status(BookingStatus.PENDING.name())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .user(UserEntity.builder().id(UUID.randomUUID()).build())
                .service(ServiceEntity.builder().id(UUID.randomUUID()).build())
                .build();

        // when
        Booking booking = bookingMapper.toBooking(entity);

        // then
        assertEquals(entity.getStatus(), booking.getStatus());
        assertEquals(entity.getId().toString(), booking.getId());
    }

    @Test
    void shouldMapBookingToEntity() {
        // given
        Booking booking = Booking.builder()
                .id(UUID.randomUUID().toString())
                .status(BookingStatus.PENDING.name())
                .dateTime(Instant.now())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        // when
        BookingEntity entity = bookingMapper.toBookingsEntity(booking);

        // then
        assertEquals(booking.getStatus(), entity.getStatus());
        assertEquals(booking.getDateTime(), entity.getDateTime());
    }

    @Test
    void shouldMapBookingToDetailsDto() {
        // given
        Booking booking = Booking.builder()
                .id("booking1")
                .userId("user1")
                .serviceId("service1")
                .status(BookingStatus.PENDING.name())
                .dateTime(Instant.now())
                .build();

        // when
        BookingDetailsDto dto = bookingMapper.toBookingDetailsDto(booking, "Taras", "Shevchuk", "Massage");

        // then
        assertEquals(booking.getId(), dto.getId());
        assertEquals("Taras", dto.getFirstName());
        assertEquals("Massage", dto.getServiceName());
    }
}