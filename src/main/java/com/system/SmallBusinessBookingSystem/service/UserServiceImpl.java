package com.system.SmallBusinessBookingSystem.service;

import com.system.SmallBusinessBookingSystem.exception.UserNotFoundException;
import com.system.SmallBusinessBookingSystem.mapper.UserMapper;
import com.system.SmallBusinessBookingSystem.repository.UserRepository;
import com.system.SmallBusinessBookingSystem.repository.entity.UserEntity;
import com.system.SmallBusinessBookingSystem.service.domain.User;
import com.system.SmallBusinessBookingSystem.service.domain.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

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
        Optional<UserEntity> optionalUserEntity = userRepository.findById(UUID.fromString(id));
        if (optionalUserEntity.isPresent()) {
            return userMapper.toUser(optionalUserEntity.get());
        }
        throw new UserNotFoundException("User with id " + id + " not found");
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUser)
                .toList();
    }

    @Override
    public void updateUser(User user) {
        log.info("Updating user with id: {}", user.getId());
        Optional<UserEntity> optionalUserEntity = userRepository.findById(UUID.fromString(user.getId()));
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();

            userEntity.setFirstName(user.getFirstName());
            userEntity.setLastName(user.getLastName());
            userEntity.setEmail(user.getEmail());
            userEntity.setPhoneNumber(user.getPhoneNumber());
            userEntity.setType(user.getType().name());
            userEntity.setStatus(user.getStatus().name());

            userRepository.save(userEntity);
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
