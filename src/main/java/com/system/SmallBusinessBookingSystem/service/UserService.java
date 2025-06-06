package com.system.SmallBusinessBookingSystem.service;

import com.system.SmallBusinessBookingSystem.service.domain.User;

import java.util.List;

public interface UserService {
    void createUser(User user);

    User getUser(String id);

    List<User> getAllUsers();

    void updateUser(User user);

    void deleteUser(String id);
}
