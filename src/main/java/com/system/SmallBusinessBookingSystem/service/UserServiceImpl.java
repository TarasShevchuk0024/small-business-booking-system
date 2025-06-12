package com.system.SmallBusinessBookingSystem.service;

import com.system.SmallBusinessBookingSystem.exception.UserNotFoundException;
import com.system.SmallBusinessBookingSystem.mapper.UserMapper;
import com.system.SmallBusinessBookingSystem.repository.UserRepository;
import com.system.SmallBusinessBookingSystem.service.domain.User;
import com.system.SmallBusinessBookingSystem.service.domain.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private Map<String, User> userMap = new HashMap<>();

    @Override
    public void createUser(User user) {
        log.info("Creating new user");

        user.setStatus(UserStatus.PENDING);
        user.setCreatedAt(Instant.now());

        userRepository.save(userMapper.toUserEntity(user));

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
        if (userRepository.existsById(UUID.fromString(id))) {
            userRepository.deleteById(UUID.fromString(id));
        } else {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
    }
}
