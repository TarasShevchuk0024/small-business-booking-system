package com.system.SmallBusinessBookingSystem.mapper;

import com.system.SmallBusinessBookingSystem.controller.dto.ServiceCreateDto;
import com.system.SmallBusinessBookingSystem.controller.dto.ServiceDetailsDto;
import com.system.SmallBusinessBookingSystem.controller.dto.ServiceUpdateDto;
import com.system.SmallBusinessBookingSystem.repository.entity.ServiceEntity;
import com.system.SmallBusinessBookingSystem.service.models.Service;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ServiceMapperTest {

    private final ServiceMapper serviceMapper = new ServiceMapper();

    @Test
    void shouldMapCreateDtoToService() {
        // given
        ServiceCreateDto dto = ServiceCreateDto.builder()
                .serviceName("Massage")
                .description("Relax")
                .durationMinutes(60)
                .priceEur(50.0)
                .build();

        // when
        Service service = serviceMapper.toService(dto);

        // then
        assertEquals(dto.getServiceName(), service.getServiceName());
        assertEquals(dto.getDescription(), service.getDescription());
        assertEquals(dto.getDurationMinutes(), service.getDuration());
        assertEquals(dto.getPriceEur(), service.getPrice());
    }

    @Test
    void shouldMapUpdateDtoToService() {
        // given
        ServiceUpdateDto dto = ServiceUpdateDto.builder()
                .id("001")
                .serviceName("Updated")
                .description("New information")
                .durationMinutes(90)
                .priceEur(100.0)
                .build();

        // when
        Service service = serviceMapper.toService(dto);

        // then
        assertEquals(dto.getId(), service.getId());
        assertEquals(dto.getServiceName(), service.getServiceName());
        assertEquals(dto.getDescription(), service.getDescription());
        assertEquals(dto.getDurationMinutes(), service.getDuration());
        assertEquals(dto.getPriceEur(), service.getPrice());
        assertNotNull(service.getUpdatedAt());
    }

    @Test
    void shouldMapServiceToEntity() {
        // given
        Service service = Service.builder()
                .id(UUID.randomUUID().toString())
                .serviceName("ToEntity")
                .description("Description")
                .duration(30)
                .price(25.0)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        // when
        ServiceEntity entity = serviceMapper.toServiceEntity(service);

        // then
        assertEquals(service.getServiceName(), entity.getServiceName());
        assertEquals(service.getDescription(), entity.getDescription());
        assertEquals(service.getDuration(), entity.getDuration());
        assertEquals(service.getPrice(), entity.getPrice());
    }

    @Test
    void shouldMapEntityToService() {
        // given
        ServiceEntity entity = ServiceEntity.builder()
                .id(UUID.randomUUID())
                .serviceName("FromEntity")
                .description("Description")
                .duration(45)
                .price(40.0)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        // when
        Service service = serviceMapper.toService(entity);

        // then
        assertEquals(entity.getId().toString(), service.getId());
        assertEquals(entity.getServiceName(), service.getServiceName());
        assertEquals(entity.getDescription(), service.getDescription());
        assertEquals(entity.getDuration(), service.getDuration());
        assertEquals(entity.getPrice(), service.getPrice());
    }

    @Test
    void shouldMapServiceToDetailsDto() {
        // given
        Service service = Service.builder()
                .id("001")
                .serviceName("Test")
                .description("Description")
                .duration(20)
                .price(15.0)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        // when
        ServiceDetailsDto dto = serviceMapper.toServiceDetailsDto(service);

        // then
        assertEquals(service.getId(), dto.getId());
        assertEquals(service.getServiceName(), dto.getServiceName());
        assertEquals(service.getDescription(), dto.getDescription());
        assertEquals(service.getDuration(), dto.getDurationMinutes());
        assertEquals(service.getPrice(), dto.getPriceEur());
    }
}
