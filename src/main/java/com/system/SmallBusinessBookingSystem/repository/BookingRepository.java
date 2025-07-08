package com.system.SmallBusinessBookingSystem.repository;

import com.system.SmallBusinessBookingSystem.repository.entity.BookingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<BookingsEntity, UUID> {

    List<BookingsEntity> findByUserId(UUID userId);

    List<BookingsEntity> findByServiceId(UUID serviceId);

    Optional<BookingsEntity> findByIdAndUserId(UUID bookingId, UUID userId);

    boolean existsByServiceIdAndDateTime(UUID serviceId, Instant dateTime);
}