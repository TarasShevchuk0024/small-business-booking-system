package com.system.SmallBusinessBookingSystem.controller;

import com.system.SmallBusinessBookingSystem.controller.dto.BusinessCreateDto;
import com.system.SmallBusinessBookingSystem.controller.dto.BusinessDetailsDto;
import com.system.SmallBusinessBookingSystem.controller.dto.BusinessUpdateDto;
import com.system.SmallBusinessBookingSystem.mapper.BusinessMapper;
import com.system.SmallBusinessBookingSystem.service.business.BusinessService;
import com.system.SmallBusinessBookingSystem.service.models.Business;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BusinessControllerTest {

    @Mock
    private BusinessService businessService;

    @Mock
    private BusinessMapper businessMapper;

    @InjectMocks
    private BusinessController businessController;

    @Test
    void shouldSuccessfullyCreateBusiness() {
        // given
        BusinessCreateDto dto = mock(BusinessCreateDto.class);
        Business business = mock(Business.class);

        when(businessMapper.toBusiness(dto)).thenReturn(business);

        // when
        ResponseEntity<Void> response = businessController.createBusiness(dto);

        // then
        verify(businessMapper).toBusiness(dto);
        verify(businessService).createBusiness(business);
        assertEquals(201, response.getStatusCode().value());
    }

    @Test
    void shouldReturnListOfBusinesses() {
        // given
        Business business = Business.builder()
                .id("001")
                .businessName("Shop")
                .description("Auto parts store")
                .build();

        when(businessService.getAllBusinesses()).thenReturn(List.of(business));
        when(businessMapper.toBusinessDetailsDto(any())).thenReturn(
                BusinessDetailsDto.builder().id("001").businessName("Shop").build()
        );

        // when
        ResponseEntity<List<BusinessDetailsDto>> response = businessController.getAllBusinesses();

        // then
        verify(businessService).getAllBusinesses();
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void shouldUpdateBusinessWhenIdsMatch() {
        // given
        BusinessUpdateDto dto = BusinessUpdateDto.builder()
                .id("001")
                .businessName("Store")
                .build();
        Business business = mock(Business.class);

        when(businessMapper.toBusiness(dto)).thenReturn(business);

        // when
        ResponseEntity<Void> response = businessController.updateBusiness("001", dto);

        // then
        verify(businessMapper).toBusiness(dto);
        verify(businessService).updateBusiness(business);
        assertEquals(200, response.getStatusCode().value());
    }

}