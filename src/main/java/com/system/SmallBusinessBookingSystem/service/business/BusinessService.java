package com.system.SmallBusinessBookingSystem.service.business;

import com.system.SmallBusinessBookingSystem.service.models.Business;

import java.util.List;

public interface BusinessService {
    void createBusiness(Business business);

    List<Business> getAllBusinesses();

    List<Business> getBusinessesForCurrentUser();

    Business getBusinessById(String id);

    void updateBusiness(Business business);

    void deleteBusiness(String id);
}