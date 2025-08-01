package com.system.SmallBusinessBookingSystem.mapper;

import com.system.SmallBusinessBookingSystem.controller.dto.BusinessCreateDto;
import com.system.SmallBusinessBookingSystem.controller.dto.BusinessDetailsDto;
import com.system.SmallBusinessBookingSystem.controller.dto.BusinessUpdateDto;
import com.system.SmallBusinessBookingSystem.repository.entity.BusinessEntity;
import com.system.SmallBusinessBookingSystem.service.models.Business;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class BusinessMapperTest {

    private final BusinessMapper businessMapper = new BusinessMapper();

    @Test
    void shouldMapCreateDtoToBusiness() {
        // given
        BusinessCreateDto dto = BusinessCreateDto.builder()
                .businessName("Bakery")
                .description("Baking bread")
                .build();

        // when
        Business business = businessMapper.toBusiness(dto);

        // then
        assertEquals(dto.getBusinessName(), business.getBusinessName());
        assertEquals(dto.getDescription(), business.getDescription());
        assertNull(business.getId());
    }

    @Test
    void shouldMapUpdateDtoToBusiness() {
        // given
        BusinessUpdateDto dto = BusinessUpdateDto.builder()
                .id("002")
                .businessName("Laundry")
                .description("Washing clothes")
                .build();

        // when
        Business business = businessMapper.toBusiness(dto);

        // then
        assertEquals(dto.getId(), business.getId());
        assertEquals(dto.getBusinessName(), business.getBusinessName());
        assertEquals(dto.getDescription(), business.getDescription());
    }

    @Test
    void shouldMapBusinessToEntity() {
        // given
        Business business = Business.builder()
                .id(UUID.randomUUID().toString())
                .businessName("Bakery")
                .description("Baking bread")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        // when
        BusinessEntity entity = businessMapper.toBusinessEntity(business);

        // then
        assertEquals(business.getBusinessName(), entity.getBusinessName());
        assertEquals(business.getDescription(), entity.getDescription());
        assertEquals(business.getCreatedAt(), entity.getCreatedAt());
        assertEquals(business.getUpdatedAt(), entity.getUpdatedAt());
    }

    @Test
    void shouldMapEntityToBusiness() {
        // given
        BusinessEntity entity = BusinessEntity.builder()
                .id(UUID.randomUUID())
                .businessName("Bakery")
                .description("Baking bread")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        // when
        Business business = businessMapper.toBusiness(entity);

        // then
        assertEquals(entity.getId().toString(), business.getId());
        assertEquals(entity.getBusinessName(), business.getBusinessName());
        assertEquals(entity.getDescription(), business.getDescription());
    }

    @Test
    void shouldMapBusinessToDetailsDto() {
        // given
        Business business = Business.builder()
                .id("002")
                .businessName("Laundry")
                .description("Washing clothes")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        // when
        BusinessDetailsDto dto = businessMapper.toBusinessDetailsDto(business);

        // then
        assertEquals(business.getId(), dto.getId());
        assertEquals(business.getBusinessName(), dto.getBusinessName());
        assertEquals(business.getDescription(), dto.getDescription());
    }
}