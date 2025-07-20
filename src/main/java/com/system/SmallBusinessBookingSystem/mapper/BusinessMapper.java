package com.system.SmallBusinessBookingSystem.mapper;

import com.system.SmallBusinessBookingSystem.controller.dto.BusinessCreateDto;
import com.system.SmallBusinessBookingSystem.controller.dto.BusinessDetailsDto;
import com.system.SmallBusinessBookingSystem.controller.dto.BusinessUpdateDto;
import com.system.SmallBusinessBookingSystem.repository.entity.BusinessEntity;
import com.system.SmallBusinessBookingSystem.service.models.Business;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BusinessMapper {

    public Business toBusiness(BusinessCreateDto dto) {
        return Business.builder()
                .businessName(dto.getBusinessName())
                .description(dto.getDescription())
                .build();
    }

    public Business toBusiness(BusinessUpdateDto dto) {
        return Business.builder()
                .id(dto.getId())
                .businessName(dto.getBusinessName())
                .description(dto.getDescription())
                .build();
    }

    public Business toBusiness(BusinessEntity entity) {
        return Business.builder()
                .id(entity.getId().toString())
                .businessName(entity.getBusinessName())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public BusinessEntity toBusinessEntity(Business business) {
        return BusinessEntity.builder()
                .id(business.getId() != null ? UUID.fromString(business.getId()) : null)
                .businessName(business.getBusinessName())
                .description(business.getDescription())
                .createdAt(business.getCreatedAt())
                .updatedAt(business.getUpdatedAt())
                .build();
    }

    public BusinessDetailsDto toBusinessDetailsDto(Business business) {
        return BusinessDetailsDto.builder()
                .id(business.getId())
                .businessName(business.getBusinessName())
                .description(business.getDescription())
                .createdAt(business.getCreatedAt())
                .updatedAt(business.getUpdatedAt())
                .build();
    }
}