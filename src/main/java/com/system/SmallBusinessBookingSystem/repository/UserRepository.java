package com.system.SmallBusinessBookingSystem.repository;

import com.system.SmallBusinessBookingSystem.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByToken(UUID token);

    Optional<UserEntity> findByEmailIgnoreCase(String email);

    List<UserEntity> findByStatus(String status);

    List<UserEntity> findByType(String type);

    List<UserEntity> findByStatusAndType(String status, String type);
}