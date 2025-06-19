package com.system.SmallBusinessBookingSystem.service.user;

import com.system.SmallBusinessBookingSystem.service.models.User;

import java.util.List;

public interface UserService {
    void createUser(User user);

    String registerUser(User user);

    User getUser(String id);

    User getByToken(String token);

    List<User> getAllUsers();

    void updateUser(User user);

    void deleteUser(String id);

    void completeRegistration(String token, String password);

    User getAuthenticatedUser();
}
