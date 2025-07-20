package com.system.SmallBusinessBookingSystem.service.service;

import com.system.SmallBusinessBookingSystem.exception.BusinessNotFoundException;
import com.system.SmallBusinessBookingSystem.exception.ServiceNotFoundException;
import com.system.SmallBusinessBookingSystem.mapper.ServiceMapper;
import com.system.SmallBusinessBookingSystem.repository.BusinessRepository;
import com.system.SmallBusinessBookingSystem.repository.ServiceRepository;
import com.system.SmallBusinessBookingSystem.repository.entity.BusinessEntity;
import com.system.SmallBusinessBookingSystem.repository.entity.ServiceEntity;
import com.system.SmallBusinessBookingSystem.service.models.Service;
import com.system.SmallBusinessBookingSystem.service.models.User;
import com.system.SmallBusinessBookingSystem.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@Slf4j
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final BusinessRepository businessRepository;
    private final ServiceMapper serviceMapper;
    private final UserService userService;

    @Override
    public void createService(Service service, String businessId) {
        log.info("Creating service: {}", service);

        User user = userService.getAuthenticatedUser();
        UUID bId = UUID.fromString(businessId);
        UUID uId = UUID.fromString(user.getId());

        BusinessEntity businessEntity = businessRepository.findByIdAndUserId(bId, uId)
                .orElseThrow(() -> new BusinessNotFoundException("Business not found or access denied"));

        ServiceEntity serviceEntity = serviceMapper.toServiceEntity(service);
        serviceEntity.setBusiness(businessEntity);

        serviceRepository.save(serviceEntity);
    }

    @Override
    public List<Service> getAllServicesForBusiness(String businessId) {
        UUID bId = UUID.fromString(businessId);

        return serviceRepository.findByBusinessId(bId)
                .stream()
                .map(serviceMapper::toService)
                .toList();
    }

    @Override
    public Service getService(String id, String businessId) {
        UUID sId = UUID.fromString(id);
        UUID bId = UUID.fromString(businessId);

        ServiceEntity entity = serviceRepository.findByIdAndBusinessId(sId, bId)
                .orElseThrow(() -> new ServiceNotFoundException("Service not found or access denied"));

        return serviceMapper.toService(entity);
    }

    @Override
    public void updateService(Service service, String businessId) {
        UUID sId = UUID.fromString(service.getId());
        UUID bId = UUID.fromString(businessId);

        ServiceEntity entity = serviceRepository.findByIdAndBusinessId(sId, bId)
                .orElseThrow(() -> new ServiceNotFoundException("Service not found or access denied"));

        entity.setServiceName(service.getServiceName());
        entity.setDescription(service.getDescription());
        entity.setDuration(service.getDuration());
        entity.setPrice(service.getPrice());
        entity.setUpdatedAt(service.getUpdatedAt());

        serviceRepository.save(entity);
    }

    @Override
    public void deleteService(String id, String businessId) {
        UUID sId = UUID.fromString(id);
        UUID bId = UUID.fromString(businessId);

        ServiceEntity entity = serviceRepository.findByIdAndBusinessId(sId, bId)
                .orElseThrow(() -> new ServiceNotFoundException("Service not found or access denied"));

        serviceRepository.delete(entity);
    }
}