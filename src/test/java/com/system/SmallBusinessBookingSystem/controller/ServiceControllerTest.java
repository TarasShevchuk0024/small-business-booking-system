package com.system.SmallBusinessBookingSystem.controller;

import com.system.SmallBusinessBookingSystem.controller.dto.ServiceCreateDto;
import com.system.SmallBusinessBookingSystem.controller.dto.ServiceDetailsDto;
import com.system.SmallBusinessBookingSystem.controller.dto.ServiceUpdateDto;
import com.system.SmallBusinessBookingSystem.mapper.ServiceMapper;
import com.system.SmallBusinessBookingSystem.service.models.Service;
import com.system.SmallBusinessBookingSystem.service.service.ServiceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceControllerTest {

    @Mock
    private ServiceService serviceService;

    @Mock
    private ServiceMapper serviceMapper;

    @InjectMocks
    private ServiceController serviceController;

    @Test
    void shouldReturnAllServicesForBusiness() {
        // given
        String businessId = "businessTest";
        Service service = Service.builder().id("serviceTest").serviceName("Haircut").build();

        // when
        when(serviceService.getAllServicesForBusiness(businessId)).thenReturn(List.of(service));
        when(serviceMapper.toServiceDetailsDto(service)).thenReturn(
                ServiceDetailsDto.builder().id("serviceTest").serviceName("Haircut").build()
        );

        ResponseEntity<List<ServiceDetailsDto>> response = serviceController.getAllServices(businessId);

        // then
        verify(serviceService).getAllServicesForBusiness(businessId);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void shouldReturnServiceById() {
        // given
        String businessId = "businessTest";
        String serviceId = "serviceTest";
        Service service = Service.builder().id(serviceId).serviceName("Massage").build();

        // when
        when(serviceService.getService(serviceId, businessId)).thenReturn(service);
        when(serviceMapper.toServiceDetailsDto(service)).thenReturn(
                ServiceDetailsDto.builder().id(serviceId).serviceName("Massage").build()
        );

        ResponseEntity<ServiceDetailsDto> response = serviceController.getServiceById(businessId, serviceId);

        // then
        verify(serviceService).getService(serviceId, businessId);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Massage", response.getBody().getServiceName());
    }

    @Test
    void shouldCreateService() {
        // given
        String businessId = "businessTest";
        ServiceCreateDto dto = ServiceCreateDto.builder().serviceName("Pedicure").build();
        Service service = Service.builder().serviceName("Pedicure").build();

        // when
        when(serviceMapper.toService(dto)).thenReturn(service);

        ResponseEntity<Void> response = serviceController.createService(businessId, dto);

        // then
        verify(serviceMapper).toService(dto);
        verify(serviceService).createService(service, businessId);
        assertEquals(201, response.getStatusCode().value());
    }

    @Test
    void shouldUpdateService() {
        // given
        String businessId = "businessTest";
        String serviceId = "serviceTest";
        ServiceUpdateDto dto = ServiceUpdateDto.builder().id(serviceId).serviceName("Updated").build();
        Service service = Service.builder().id(serviceId).serviceName("Updated").build();

        // when
        when(serviceMapper.toService(dto)).thenReturn(service);

        ResponseEntity<Void> response = serviceController.updateService(businessId, serviceId, dto);

        // then
        verify(serviceMapper).toService(dto);
        verify(serviceService).updateService(service, businessId);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void shouldDeleteService() {
        // given
        String businessId = "businessTest";
        String serviceId = "serviceTest";

        // when
        ResponseEntity<Void> response = serviceController.deleteService(businessId, serviceId);

        // then
        verify(serviceService).deleteService(serviceId, businessId);
        assertEquals(204, response.getStatusCode().value());
    }
}