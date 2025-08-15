package com.system.SmallBusinessBookingSystem.service.business;

import com.system.SmallBusinessBookingSystem.mapper.BusinessMapper;
import com.system.SmallBusinessBookingSystem.mapper.UserMapper;
import com.system.SmallBusinessBookingSystem.repository.BusinessRepository;
import com.system.SmallBusinessBookingSystem.repository.entity.BusinessEntity;
import com.system.SmallBusinessBookingSystem.service.models.Business;
import com.system.SmallBusinessBookingSystem.service.models.User;
import com.system.SmallBusinessBookingSystem.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BusinessServiceImplTest {

    @Mock
    private BusinessRepository businessRepository;

    @Mock
    private BusinessMapper businessMapper;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private BusinessServiceImpl businessService;

    @Test
    void shouldCreateBusiness() {
        // given
        Business business = mock(Business.class);
        User user = mock(User.class);
        BusinessEntity entity = mock(BusinessEntity.class);

        // when
        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(businessMapper.toBusinessEntity(business)).thenReturn(entity);

        businessService.createBusiness(business);

        // then
        verify(userService).getAuthenticatedUser();
        verify(businessMapper).toBusinessEntity(business);
        verify(entity).setUser(any());
        verify(businessRepository).save(entity);
    }

    @Test
    void shouldUpdateBusiness() {
        // given
        Business business = Business.builder()
                .id(UUID.randomUUID().toString())
                .businessName("Updated")
                .build();

        User user = User.builder()
                .id(UUID.randomUUID())
                .build();

        BusinessEntity entity = new BusinessEntity();
        entity.setId(UUID.fromString(business.getId()));

        // when
        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(businessRepository.findByIdAndUserId(any(), any())).thenReturn(Optional.of(entity));

        businessService.updateBusiness(business);

        // then
        verify(businessRepository).save(entity);
    }

    @Test
    void shouldDeleteBusiness() {
        // given
        String id = UUID.randomUUID().toString();
        User user = User.builder().id(UUID.randomUUID()).build();
        BusinessEntity entity = new BusinessEntity();

        // when
        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(businessRepository.findByIdAndUserId(any(), any())).thenReturn(Optional.of(entity));

        businessService.deleteBusiness(id);

        // then
        verify(businessRepository).delete(entity);
    }
}
