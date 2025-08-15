package com.system.SmallBusinessBookingSystem.service.user;

import com.system.SmallBusinessBookingSystem.exception.PasswordIncorrectException;
import com.system.SmallBusinessBookingSystem.exception.UserNotFoundException;
import com.system.SmallBusinessBookingSystem.mapper.UserMapper;
import com.system.SmallBusinessBookingSystem.repository.UserRepository;
import com.system.SmallBusinessBookingSystem.repository.entity.UserEntity;
import com.system.SmallBusinessBookingSystem.service.email.EmailService;
import com.system.SmallBusinessBookingSystem.service.models.User;
import com.system.SmallBusinessBookingSystem.service.models.UserStatus;
import com.system.SmallBusinessBookingSystem.service.token.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final EmailService emailService;
    private final HttpServletRequest request;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Value("${frontend.base-url}")
    private String frontendBaseUrl;

    @Override
    public void createUser(User user) {
        log.info("Creating new user");

        user.setStatus(UserStatus.PENDING);
        user.setCreatedAt(Instant.now());

        UUID token = UUID.randomUUID();
        user.setToken(token);

        userRepository.save(userMapper.toUserEntity(user));

        emailService.sendEmail(
                user.getEmail(),
                "Confirm registration",
                "Please confirm your registration by clicking the link below:\n"
                        + frontendBaseUrl
                        + "/users/registration-confirm?token="
                        + token);

        log.info("User created: {}", user);
    }

    @Override
    public String registerUser(User user) {

        log.info("Creating new user");

        user.setStatus(UserStatus.ACTIVE);
        user.setCreatedAt(Instant.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserEntity registeredUserEntity = userRepository.save(userMapper.toUserEntity(user));

        return tokenService.createToken(userMapper.toUser(registeredUserEntity));

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
    public User getByToken(String token) {

        Optional<UserEntity> optionalUserEntity = userRepository.findByToken(UUID.fromString(token));
        if (optionalUserEntity.isPresent()) {
            return userMapper.toUser(optionalUserEntity.get());
        }

        throw new UserNotFoundException("User with token " + token + " not found");
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
        Optional<UserEntity> optionalUserEntity =
                userRepository.findById(user.getId());
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();

            userEntity.setFirstName(user.getFirstName());
            userEntity.setLastName(user.getLastName());
            userEntity.setEmail(user.getEmail());
            userEntity.setPhoneNumber(user.getPhoneNumber());
            userEntity.setType(user.getType().name());
            userEntity.setStatus(user.getStatus().name());
            userEntity.setUpdatedAt(Instant.now());


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

    @Override
    public void completeRegistration(String token, String password) {

        Optional<UserEntity> optionalUserEntity = userRepository.findByToken(UUID.fromString(token));
        if (optionalUserEntity.isPresent()) {

            UserEntity userEntity = optionalUserEntity.get();

            userEntity.setPassword(passwordEncoder.encode(password));
            userEntity.setStatus("ACTIVE");
            userEntity.setUpdatedAt(Instant.now());
            userEntity.setToken(null);

            userRepository.save(userEntity);
        } else {
            throw new UserNotFoundException("User with id " + token + " not found");
        }
    }

    @Override
    public User getAuthenticatedUser() {

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        return getUser(userId);
    }

    @Override
    public void changeOwnPassword(String oldPassword, String newPassword) {
        User current = getAuthenticatedUser();

        var entity = userRepository.findById(current.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(oldPassword, entity.getPassword())) {
            throw new PasswordIncorrectException("Old password is incorrect");
        }

        entity.setPassword(passwordEncoder.encode(newPassword));
        entity.setUpdatedAt(Instant.now());
        userRepository.save(entity);
    }

    @Override
    public void requestPasswordReset(String email) {
        // Не розкриваємо, чи існує емейл (анти-Enumeration), але якщо є — шлемо листа.
        userRepository.findByEmailIgnoreCase(email).ifPresent(entity -> {
            UUID token = UUID.randomUUID();
            entity.setToken(token);
            entity.setUpdatedAt(Instant.now());
            userRepository.save(entity);

            emailService.sendEmail(
                    email,
                    "Password reset",
                    "Click the link to reset your password:\n"
                            + frontendBaseUrl
                            + "/users/password-reset?token=" + token
            );
        });
    }

    @Override
    public User verifyPasswordResetToken(String token) {
        return userRepository.findByToken(UUID.fromString(token))
                .map(userMapper::toUser)
                .orElseThrow(() -> new UserNotFoundException("Reset token not found"));
    }

    @Override
    public void completePasswordReset(String token, String newPassword) {
        var entity = userRepository.findByToken(UUID.fromString(token))
                .orElseThrow(() -> new UserNotFoundException("Reset token not found"));

        entity.setPassword(passwordEncoder.encode(newPassword));
        entity.setToken(null); // інвалідовуємо токен
        entity.setUpdatedAt(Instant.now());
        userRepository.save(entity);
    }

    @Override
    public void adminSetPassword(String targetUserId, String newPassword) {
        var entity = userRepository.findById(UUID.fromString(targetUserId))
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        entity.setPassword(passwordEncoder.encode(newPassword));
        entity.setUpdatedAt(Instant.now());
        userRepository.save(entity);
    }
}