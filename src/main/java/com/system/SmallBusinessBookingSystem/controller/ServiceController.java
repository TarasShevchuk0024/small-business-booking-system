package com.system.SmallBusinessBookingSystem.controller;

import com.system.SmallBusinessBookingSystem.controller.dto.ServiceCreateDto;
import com.system.SmallBusinessBookingSystem.controller.dto.ServiceDetailsDto;
import com.system.SmallBusinessBookingSystem.controller.dto.ServiceUpdateDto;
import com.system.SmallBusinessBookingSystem.mapper.ServiceMapper;
import com.system.SmallBusinessBookingSystem.service.models.Service;
import com.system.SmallBusinessBookingSystem.service.service.ServiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/businesses/{businessId}/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceService serviceService;
    private final ServiceMapper serviceMapper;

    @GetMapping
    public ResponseEntity<List<ServiceDetailsDto>> getAllServices(@PathVariable String businessId) {
        List<ServiceDetailsDto> services = serviceService.getAllServicesForBusiness(businessId).stream()
                .map(serviceMapper::toServiceDetailsDto)
                .toList();

        if (services.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(services);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceDetailsDto> getServiceById(@PathVariable String businessId, @PathVariable String id) {
        Service service = serviceService.getService(id, businessId);
        return ResponseEntity.ok(serviceMapper.toServiceDetailsDto(service));
    }

    @PostMapping
    public ResponseEntity<Void> createService(@PathVariable String businessId, @RequestBody ServiceCreateDto dto) {
        serviceService.createService(serviceMapper.toService(dto), businessId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateService(
            @PathVariable String businessId,
            @PathVariable String id,
            @RequestBody ServiceUpdateDto dto
    ) {
        Service service = serviceMapper.toService(dto);
        service.setId(id);
        serviceService.updateService(service, businessId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable String businessId, @PathVariable String id) {
        serviceService.deleteService(id, businessId);
        return ResponseEntity.noContent().build();
    }
}