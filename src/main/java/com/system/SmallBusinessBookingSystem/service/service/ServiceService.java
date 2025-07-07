package com.system.SmallBusinessBookingSystem.service.service;

import com.system.SmallBusinessBookingSystem.service.models.Service;

import java.util.List;

public interface ServiceService {
    void createService(Service service, String businessId);

    Service getService(String id, String businessId);

    List<Service> getAllServicesForBusiness(String businessId);

    void updateService(Service service, String businessId);

    void deleteService(String id, String businessId);
}

