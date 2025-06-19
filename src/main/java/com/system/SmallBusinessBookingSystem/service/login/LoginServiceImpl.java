package com.system.SmallBusinessBookingSystem.service.login;


import com.system.SmallBusinessBookingSystem.exception.PasswordIncorrectException;
import com.system.SmallBusinessBookingSystem.exception.UserIsBlockedException;
import com.system.SmallBusinessBookingSystem.exception.UserNotFoundException;
import com.system.SmallBusinessBookingSystem.mapper.UserMapper;
import com.system.SmallBusinessBookingSystem.repository.UserRepository;
import com.system.SmallBusinessBookingSystem.service.models.User;
import com.system.SmallBusinessBookingSystem.service.models.UserStatus;
import com.system.SmallBusinessBookingSystem.service.token.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Override
    public String login(String email, String password) {

        User user =
                userRepository
                        .findByEmailIgnoreCase(email)
                        .map(userMapper::toUser)
                        .orElseThrow(() -> new UserNotFoundException("User with " + email + " not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordIncorrectException("Password was incorrect for user with email " + email);
        }

        if (user.getStatus() == UserStatus.BLOCKED) {
            log.warn("User with email " + email + " is blocked");
            throw new UserIsBlockedException("User is blocked");
        }

        return tokenService.createToken(user);
    }
}