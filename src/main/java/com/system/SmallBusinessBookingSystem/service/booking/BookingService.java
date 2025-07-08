package com.system.SmallBusinessBookingSystem.service.booking;

import com.system.SmallBusinessBookingSystem.service.models.Booking;
import com.system.SmallBusinessBookingSystem.service.models.BookingStatus;

import java.util.List;

public interface BookingService {
    void createBooking(Booking booking);

    List<Booking> getAllBookings();

    Booking getBookingById(String id);

    void updateBookingStatus(String id, BookingStatus status);

    void deleteBooking(String id);

    void acceptBooking(String id);

    void cancelBooking(String id);
}