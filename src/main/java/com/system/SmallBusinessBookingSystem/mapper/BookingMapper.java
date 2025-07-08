package com.system.SmallBusinessBookingSystem.mapper;

import com.system.SmallBusinessBookingSystem.controller.dto.BookingCreateDto;
import com.system.SmallBusinessBookingSystem.controller.dto.BookingDetailsDto;
import com.system.SmallBusinessBookingSystem.controller.dto.BookingUpdateDto;
import com.system.SmallBusinessBookingSystem.repository.entity.BookingsEntity;
import com.system.SmallBusinessBookingSystem.service.models.Booking;
import com.system.SmallBusinessBookingSystem.service.models.BookingStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BookingMapper {

    public Booking toBooking(BookingCreateDto dto) {
        return Booking.builder()
                .userId(dto.getUserId())
                .serviceId(dto.getServiceId())
                .dateTime(dto.getDateTime())
                .status(BookingStatus.PENDING.name())
                .build();
    }

    public Booking toBooking(BookingUpdateDto dto) {
        return Booking.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .serviceId(dto.getServiceId())
                .dateTime(dto.getDateTime())
                .status(dto.getStatus())
                .build();
    }

    public Booking toBooking(BookingsEntity entity) {
        return Booking.builder()
                .id(entity.getId().toString())
                .userId(entity.getUser().getId().toString())
                .serviceId(entity.getService().getId().toString())
                .dateTime(entity.getDateTime())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public BookingsEntity toBookingsEntity(Booking booking) {
        BookingsEntity entity = new BookingsEntity();
        if (booking.getId() != null) {
            entity.setId(UUID.fromString(booking.getId()));
        }
        entity.setDateTime(booking.getDateTime());
        entity.setStatus(booking.getStatus());
        entity.setCreatedAt(booking.getCreatedAt());
        entity.setUpdatedAt(booking.getUpdatedAt());
        return entity;
    }

    public BookingDetailsDto toBookingDetailsDto(Booking booking, String firstName, String lastName, String serviceName) {
        return BookingDetailsDto.builder()
                .id(booking.getId())
                .userId(booking.getUserId())
                .firstName(firstName)
                .lastName(lastName)
                .serviceId(booking.getServiceId())
                .serviceName(serviceName)
                .dateTime(booking.getDateTime())
                .status(booking.getStatus())
                .createdAt(booking.getCreatedAt())
                .updatedAt(booking.getUpdatedAt())
                .build();
    }
}
