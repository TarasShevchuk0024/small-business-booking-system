package com.system.SmallBusinessBookingSystem.repository;

import com.system.SmallBusinessBookingSystem.repository.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, UUID> {
    Optional<NotificationEntity> findByUserIdAndBookingId(UUID userId, UUID bookingId);

    List<NotificationEntity> findByUserId(UUID userId);

    List<NotificationEntity> findByBookingId(UUID bookingId);

    List<NotificationEntity> findByUserIdAndType(UUID userId, String type);
}