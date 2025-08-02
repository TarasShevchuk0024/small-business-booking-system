package com.system.SmallBusinessBookingSystem.repository;

import com.system.SmallBusinessBookingSystem.repository.entity.BookingEntity;
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
class BookingRepositoryIT {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private UserRepository userRepository;

    private UUID userId;
    private UUID serviceId;

    @BeforeEach
    void setUp() {
        bookingRepository.deleteAll();
        serviceRepository.deleteAll();
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

        BusinessEntity business = BusinessEntity.builder()
                .businessName("Massage Salon")
                .description("SPA")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .user(user)
                .build();

        businessRepository.save(business);

        ServiceEntity service = ServiceEntity.builder()
                .serviceName("Massage")
                .description("Relax")
                .duration(60)
                .price(50.0)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .business(business)
                .build();

        serviceId = serviceRepository.save(service).getId();
    }

    @Test
    void shouldSaveAndFindBooking() {
        // given
        BookingEntity booking = BookingEntity.builder()
                .dateTime(Instant.now())
                .status("PENDING")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .user(userRepository.findById(userId).orElseThrow())
                .service(serviceRepository.findById(serviceId).orElseThrow())
                .build();

        // when
        BookingEntity saved = bookingRepository.save(booking);
        assertNotNull(saved.getId());

        // then
        BookingEntity found = bookingRepository.findById(saved.getId()).orElseThrow();
        assertEquals("PENDING", found.getStatus());
        assertEquals(userId, found.getUser().getId());
        assertEquals(serviceId, found.getService().getId());
    }

    @Test
    void shouldFindByUserId() {
        // given
        BookingEntity booking = BookingEntity.builder()
                .dateTime(Instant.now())
                .status("PENDING")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .user(userRepository.findById(userId).orElseThrow())
                .service(serviceRepository.findById(serviceId).orElseThrow())
                .build();

        // when
        bookingRepository.save(booking);

        // then
        List<BookingEntity> list = bookingRepository.findByUserId(userId);
        assertEquals(1, list.size());
    }

    @Test
    void shouldCheckExistsByServiceAndDateTime() {
        // given
        Instant now = Instant.now();

        BookingEntity booking = BookingEntity.builder()
                .dateTime(now)
                .status("PENDING")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .user(userRepository.findById(userId).orElseThrow())
                .service(serviceRepository.findById(serviceId).orElseThrow())
                .build();

        // when
        bookingRepository.save(booking);

        // then
        assertTrue(bookingRepository.existsByServiceIdAndDateTime(serviceId, now));
    }
}
