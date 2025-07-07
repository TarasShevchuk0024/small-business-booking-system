package com.system.SmallBusinessBookingSystem.repository;

import com.system.SmallBusinessBookingSystem.repository.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, UUID> {
    Optional<ServiceEntity> findByIdAndBusinessId(UUID serviceId, UUID businessId);

    List<ServiceEntity> findByBusinessId(UUID businessId);
}

