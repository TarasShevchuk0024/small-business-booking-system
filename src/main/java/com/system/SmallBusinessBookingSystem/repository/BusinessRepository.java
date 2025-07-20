package com.system.SmallBusinessBookingSystem.repository;

import com.system.SmallBusinessBookingSystem.repository.entity.BusinessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BusinessRepository extends JpaRepository<BusinessEntity, UUID> {

    List<BusinessEntity> findByUserId(UUID userId);

    Optional<BusinessEntity> findByIdAndUserId(UUID businessId, UUID userId);

    boolean existsByBusinessNameIgnoreCase(String businessName);

    Optional<BusinessEntity> findByBusinessNameIgnoreCase(String businessName);

}