package com.system.SmallBusinessBookingSystem.service;

import com.system.SmallBusinessBookingSystem.exception.UserNotFoundException;
import com.system.SmallBusinessBookingSystem.service.domain.User;
import com.system.SmallBusinessBookingSystem.service.domain.UserStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private Map<String, User> userMap = new HashMap<>();

    @Override
    public void createUser(User user) {
        log.info("Creating new user");

        user.setId(UUID.randomUUID().toString());
        user.setStatus(UserStatus.PENDING);
        userMap.put(user.getId(), user);

        log.info("User created: {}", user);

    }

    @Override
    public User getUser(String id) {
        if (userMap.containsKey(id)) {
            return userMap.get(id);
        }
        throw new UserNotFoundException("User with id " + id + " not found");
    }

    @Override
    public List<User> getAllUsers() {

        return userMap.values().stream().toList();
    }

    @Override
    public void updateUser(User user) {
        log.info("Updating user with id: {}", user.getId());

        if (userMap.containsKey(user.getId())) {
            userMap.put(user.getId(), user);
            log.info("User updating: {}", user);
        } else {
            throw new UserNotFoundException("User with id " + user.getId() + " not found");
        }
    }

    @Override
    public void deleteUser(String id) {
        if (userMap.containsKey(id)) {
            log.info("Deleting user with id: {}", id);
            userMap.remove(id);
            log.info("User deleted: {}", id);
        } else {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
    }
}
