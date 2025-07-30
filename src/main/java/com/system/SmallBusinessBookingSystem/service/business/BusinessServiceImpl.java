package com.system.SmallBusinessBookingSystem.service.business;

import com.system.SmallBusinessBookingSystem.exception.BusinessNotFoundException;
import com.system.SmallBusinessBookingSystem.mapper.BusinessMapper;
import com.system.SmallBusinessBookingSystem.mapper.UserMapper;
import com.system.SmallBusinessBookingSystem.repository.BusinessRepository;
import com.system.SmallBusinessBookingSystem.repository.entity.BusinessEntity;
import com.system.SmallBusinessBookingSystem.service.models.Business;
import com.system.SmallBusinessBookingSystem.service.models.User;
import com.system.SmallBusinessBookingSystem.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessServiceImpl implements BusinessService {

    private final BusinessRepository businessRepository;
    private final BusinessMapper businessMapper;
    private final UserService userService;
    private final UserMapper UserMapper;

    @Override
    public void createBusiness(Business business) {
        log.info("Creating new business");

        User user = userService.getAuthenticatedUser();

        business.setCreatedAt(Instant.now());
        business.setUpdatedAt(Instant.now());

        BusinessEntity businessEntity = businessMapper.toBusinessEntity(business);
        businessEntity.setUser(UserMapper.toUserEntity(user));

        businessRepository.save(businessEntity);

        log.info("Business created for user {}", user.getEmail());
    }

    @Override
    public List<Business> getAllBusinesses() {
        return businessRepository.findAll()
                .stream()
                .map(businessMapper::toBusiness)
                .toList();
    }

    @Override
    public List<Business> getBusinessesForCurrentUser() {
        User user = userService.getAuthenticatedUser();
        UUID userId = UUID.fromString(user.getId().toString());

        return businessRepository.findByUserId(userId)
                .stream()
                .map(businessMapper::toBusiness)
                .toList();
    }

    @Override
    public Business getBusinessById(String id) {
        Optional<BusinessEntity> entity = businessRepository.findById(UUID.fromString(id));
        return entity
                .map(businessMapper::toBusiness)
                .orElseThrow(() -> new BusinessNotFoundException("Business with id " + id + " not found"));
    }

    @Override
    public void updateBusiness(Business business) {
        User user = userService.getAuthenticatedUser();
        UUID businessId = UUID.fromString(business.getId());
        UUID userId = UUID.fromString(user.getId().toString());

        BusinessEntity entity = businessRepository
                .findByIdAndUserId(businessId, userId)
                .orElseThrow(() -> new BusinessNotFoundException("Business not found or access denied"));

        entity.setBusinessName(business.getBusinessName());
        entity.setDescription(business.getDescription());
        entity.setUpdatedAt(Instant.now());

        businessRepository.save(entity);
    }

    @Override
    public void deleteBusiness(String id) {
        User user = userService.getAuthenticatedUser();
        UUID businessId = UUID.fromString(id);
        UUID userId = UUID.fromString(user.getId().toString());

        BusinessEntity entity = businessRepository
                .findByIdAndUserId(businessId, userId)
                .orElseThrow(() -> new BusinessNotFoundException("Business not found or access denied"));

        businessRepository.delete(entity);
    }
}