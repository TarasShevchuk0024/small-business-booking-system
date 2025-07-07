package com.system.SmallBusinessBookingSystem.controller;

import com.system.SmallBusinessBookingSystem.controller.dto.BusinessCreateDto;
import com.system.SmallBusinessBookingSystem.controller.dto.BusinessDetailsDto;
import com.system.SmallBusinessBookingSystem.controller.dto.BusinessUpdateDto;
import com.system.SmallBusinessBookingSystem.mapper.BusinessMapper;
import com.system.SmallBusinessBookingSystem.service.business.BusinessService;
import com.system.SmallBusinessBookingSystem.service.models.Business;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/businesses")
@RequiredArgsConstructor
public class BusinessController {

    private final BusinessService businessService;
    private final BusinessMapper businessMapper;

    @PostMapping
    public ResponseEntity<Void> createBusiness(@RequestBody BusinessCreateDto dto) {
        businessService.createBusiness(businessMapper.toBusiness(dto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BusinessDetailsDto>> getAllBusinesses() {
        List<BusinessDetailsDto> businesses = businessService.getAllBusinesses()
                .stream()
                .map(businessMapper::toBusinessDetailsDto)
                .toList();

        if (businesses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(businesses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusinessDetailsDto> getBusinessById(@PathVariable String id) {
        Business business = businessService.getBusinessById(id);
        return new ResponseEntity<>(businessMapper.toBusinessDetailsDto(business), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBusiness(
            @PathVariable String id,
            @RequestBody BusinessUpdateDto dto
    ) {
        if (!id.equals(dto.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        businessService.updateBusiness(businessMapper.toBusiness(dto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusiness(@PathVariable String id) {
        businessService.deleteBusiness(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
