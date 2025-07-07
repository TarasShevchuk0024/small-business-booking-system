package com.system.SmallBusinessBookingSystem.mapper;

import com.system.SmallBusinessBookingSystem.controller.dto.ServiceCreateDto;
import com.system.SmallBusinessBookingSystem.controller.dto.ServiceDetailsDto;
import com.system.SmallBusinessBookingSystem.controller.dto.ServiceUpdateDto;
import com.system.SmallBusinessBookingSystem.repository.entity.ServiceEntity;
import com.system.SmallBusinessBookingSystem.service.models.Service;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class ServiceMapper {

    public ServiceDetailsDto toServiceDetailsDto(Service service) {
        return ServiceDetailsDto.builder()
                .id(service.getId())
                .serviceName(service.getServiceName())
                .description(service.getDescription())
                .durationMinutes(service.getDuration())
                .priceEur(service.getPrice())
                .createdAt(service.getCreatedAt())
                .updatedAt(service.getUpdatedAt())
                .build();
    }

    public Service toService(ServiceCreateDto dto) {
        return Service.builder()
                .serviceName(dto.getServiceName())
                .description(dto.getDescription())
                .duration(dto.getDurationMinutes())
                .price(dto.getPriceEur())
                .createdAt(Instant.now())
                .build();
    }

    public Service toService(ServiceUpdateDto dto) {
        return Service.builder()
                .id(dto.getId())
                .serviceName(dto.getServiceName())
                .description(dto.getDescription())
                .duration(dto.getDurationMinutes())
                .price(dto.getPriceEur())
                .updatedAt(Instant.now())
                .build();
    }

    public Service toService(ServiceEntity entity) {
        return Service.builder()
                .id(entity.getId().toString())
                .serviceName(entity.getServiceName())
                .description(entity.getDescription())
                .duration(entity.getDuration())
                .price(entity.getPrice())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public ServiceEntity toServiceEntity(Service service) {
        return ServiceEntity.builder()
                .id(service.getId() != null ? UUID.fromString(service.getId()) : null)
                .serviceName(service.getServiceName())
                .description(service.getDescription())
                .duration(service.getDuration())
                .price(service.getPrice())
                .createdAt(service.getCreatedAt())
                .updatedAt(service.getUpdatedAt())
                .build();
    }
}

