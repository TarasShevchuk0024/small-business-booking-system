package com.system.SmallBusinessBookingSystem.repository;

import com.system.SmallBusinessBookingSystem.repository.entity.BusinessEntity;
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
class BusinessRepositoryIT {

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private UserRepository userRepository;

    private UUID userId;

    @BeforeEach
    void setUp() {
        businessRepository.deleteAll();
        userRepository.deleteAll();

        UserEntity user = UserEntity.builder()
                .firstName("Taras")
                .lastName("Test")
                .email("taras@test.com")
                .phoneNumber("123456789")
                .password("Taras12345")
                .status(UserStatus.ACTIVE.name())
                .type(UserType.ROLE_ADMIN.name())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        userId = userRepository.save(user).getId();
    }

    @Test
    void shouldSaveAndFindBusiness() {
        BusinessEntity business = BusinessEntity.builder()
                .businessName("My Business")
                .description("My new business")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .user(userRepository.findById(userId).orElseThrow())
                .build();

        BusinessEntity saved = businessRepository.save(business);

        assertNotNull(saved.getId());

        BusinessEntity found = businessRepository.findById(saved.getId()).orElseThrow();
        assertEquals("My Business", found.getBusinessName());
        assertEquals("My new business", found.getDescription());
        assertEquals(userId, found.getUser().getId());
    }

    @Test
    void shouldFindByUserId() {
        BusinessEntity business = BusinessEntity.builder()
                .businessName("My Business")
                .description("Test description")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .user(userRepository.findById(userId).orElseThrow())
                .build();

        businessRepository.save(business);

        List<BusinessEntity> list = businessRepository.findByUserId(userId);
        assertEquals(1, list.size());
    }

    @Test
    void shouldCheckExistsByNameIgnoreCase() {
        BusinessEntity business = BusinessEntity.builder()
                .businessName("My Business")
                .description("Another description")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .user(userRepository.findById(userId).orElseThrow())
                .build();

        businessRepository.save(business);

        assertTrue(businessRepository.existsByBusinessNameIgnoreCase("My Business"));
        assertFalse(businessRepository.existsByBusinessNameIgnoreCase("other"));
    }
}
