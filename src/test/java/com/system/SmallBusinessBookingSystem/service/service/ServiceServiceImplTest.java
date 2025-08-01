package com.system.SmallBusinessBookingSystem.service.service;

import com.system.SmallBusinessBookingSystem.mapper.ServiceMapper;
import com.system.SmallBusinessBookingSystem.repository.BusinessRepository;
import com.system.SmallBusinessBookingSystem.repository.ServiceRepository;
import com.system.SmallBusinessBookingSystem.repository.entity.BusinessEntity;
import com.system.SmallBusinessBookingSystem.repository.entity.ServiceEntity;
import com.system.SmallBusinessBookingSystem.service.models.Service;
import com.system.SmallBusinessBookingSystem.service.models.User;
import com.system.SmallBusinessBookingSystem.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceServiceImplTest {

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private BusinessRepository businessRepository;

    @Mock
    private ServiceMapper serviceMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private ServiceServiceImpl serviceService;

    @Test
    void shouldCreateService() {
        // given
        String businessId = UUID.randomUUID().toString();
        Service service = Service.builder().serviceName("Massage").build();
        User user = User.builder().id(UUID.randomUUID()).build();
        BusinessEntity businessEntity = new BusinessEntity();
        ServiceEntity entity = mock(ServiceEntity.class);

        // when
        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(businessRepository.findByIdAndUserId(any(), any())).thenReturn(Optional.of(businessEntity));
        when(serviceMapper.toServiceEntity(service)).thenReturn(entity);

        serviceService.createService(service, businessId);

        // then
        verify(serviceRepository).save(entity);
        verify(entity).setBusiness(businessEntity);
    }

    @Test
    void shouldUpdateService() {
        // given
        String businessId = UUID.randomUUID().toString();
        Service service = Service.builder().id(UUID.randomUUID().toString()).serviceName("Updated").build();
        ServiceEntity entity = new ServiceEntity();

        // when
        when(serviceRepository.findByIdAndBusinessId(any(), any())).thenReturn(Optional.of(entity));

        serviceService.updateService(service, businessId);

        // then
        verify(serviceRepository).save(entity);
    }

    @Test
    void shouldDeleteService() {
        // given
        String businessId = UUID.randomUUID().toString();
        String serviceId = UUID.randomUUID().toString();
        ServiceEntity entity = new ServiceEntity();

        // when
        when(serviceRepository.findByIdAndBusinessId(any(), any())).thenReturn(Optional.of(entity));

        serviceService.deleteService(serviceId, businessId);

        // then
        verify(serviceRepository).delete(entity);
    }
}