package com.system.SmallBusinessBookingSystem.service;

import com.system.SmallBusinessBookingSystem.service.domain.User;
import com.system.SmallBusinessBookingSystem.service.domain.UserStatus;
import com.system.SmallBusinessBookingSystem.service.domain.UserType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Override
    public void createUser(User user) {
        log.info("Creating new user");

        user.setStatus(UserStatus.PENDING);
        log.info("User created: {}", user);

    }

    @Override
    public User getUser(String id) {
        return new User(
                null,
                "Anna",
                "Shevchuk",
                "anna_shevchuk@gmail.com",
                null,
                "+380633578765",
                UserType.ADMIN,
                UserStatus.ACTIVE);
    }

    @Override
    public List<User> getAllUsers() {

        return List.of(
                new User(
                        null,
                        "Anna",
                        "Shevchuk",
                        "anna_shevchuk@gmail.com",
                        "null",
                        "380633578765",
                        UserType.ADMIN,
                        UserStatus.ACTIVE)
        );
    }

    @Override
    public void updateUser(User user) {
        log.info("Updating user with id: {}", user.getId());
        log.info("User updating: {}", user);
    }

    @Override
    public void deleteUser(String id) {

    }
}
