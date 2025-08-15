package com.system.SmallBusinessBookingSystem.repository;

import com.system.SmallBusinessBookingSystem.repository.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, UUID> {

    List<BookingEntity> findByUserId(UUID userId);

    List<BookingEntity> findByServiceId(UUID serviceId);

    Optional<BookingEntity> findByIdAndUserId(UUID bookingId, UUID userId);

    boolean existsByServiceIdAndDateTime(UUID serviceId, Instant dateTime);

    List<BookingEntity> findByStatus(String status);

    List<BookingEntity> findByDateTimeBetween(Instant start, Instant end);

    List<BookingEntity> findByUserIdAndStatus(UUID userId, String status);

    List<BookingEntity> findByServiceIdAndStatus(UUID serviceId, String status);
}