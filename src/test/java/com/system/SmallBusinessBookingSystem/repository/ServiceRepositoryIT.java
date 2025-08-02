package com.system.SmallBusinessBookingSystem.repository;

import com.system.SmallBusinessBookingSystem.repository.entity.BusinessEntity;
import com.system.SmallBusinessBookingSystem.repository.entity.ServiceEntity;
import com.system.SmallBusinessBookingSystem.repository.entity.UserEntity;
import com.system.SmallBusinessBookingSystem.service.models.UserStatus;
import com.system.SmallBusinessBookingSystem.service.models.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ServiceRepositoryIT {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private UserRepository userRepository;

    private UUID businessId;

    @BeforeEach
    void setUp() {
        serviceRepository.deleteAll();
        businessRepository.deleteAll();
        userRepository.deleteAll();

        UserEntity user = UserEntity.builder()
                .firstName("Ivan")
                .lastName("Test")
                .email("ivan@test.com")
                .phoneNumber("+380634567343")
                .password("Qwerty1111")
                .status(UserStatus.ACTIVE.name())
                .type(UserType.ROLE_ADMIN.name())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        UserEntity savedUser = userRepository.save(user);

        BusinessEntity business = BusinessEntity.builder()
                .businessName("SPA")
                .description("Relax-salon")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .user(savedUser)
                .build();
        businessId = businessRepository.save(business).getId();
    }

    @Test
    void shouldSaveAndFindService() {
        // given
        ServiceEntity service = ServiceEntity.builder()
                .serviceName("Massage")
                .description("Relax")
                .duration(60)
                .price(50.0)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .business(businessRepository.findById(businessId).orElseThrow())
                .build();

        // when
        ServiceEntity saved = serviceRepository.save(service);
        assertNotNull(saved.getId());

        // then
        ServiceEntity found = serviceRepository.findById(saved.getId()).orElseThrow();
        assertEquals("Massage", found.getServiceName());
        assertEquals("Relax", found.getDescription());
        assertEquals(businessId, found.getBusiness().getId());
    }

    @Test
    void shouldFindByBusinessId() {
        // given
        ServiceEntity service = ServiceEntity.builder()
                .serviceName("Pedicure")
                .description("Feet care")
                .duration(30)
                .price(25.0)
                .business(businessRepository.findById(businessId).orElseThrow())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        // when
        serviceRepository.save(service);

        // then
        List<ServiceEntity> list = serviceRepository.findByBusinessId(businessId);
        assertEquals(1, list.size());
    }

    @Test
    void shouldCheckExistsByNameIgnoreCase() {
        // given
        ServiceEntity service = ServiceEntity.builder()
                .serviceName("Haircut")
                .description("Cutting")
                .duration(40)
                .price(20.0)
                .business(businessRepository.findById(businessId).orElseThrow())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        // when
        serviceRepository.save(service);

        // then
        assertTrue(serviceRepository.existsByServiceNameIgnoreCaseAndBusinessId("haircut", businessId));
        assertFalse(serviceRepository.existsByServiceNameIgnoreCaseAndBusinessId("massage", businessId));
    }
}
