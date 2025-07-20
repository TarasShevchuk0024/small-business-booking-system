package com.system.SmallBusinessBookingSystem.controller;


import com.system.SmallBusinessBookingSystem.controller.dto.AuthCredentialsDto;
import com.system.SmallBusinessBookingSystem.controller.dto.AuthTokenDto;
import com.system.SmallBusinessBookingSystem.controller.dto.UserRegistrationDto;
import com.system.SmallBusinessBookingSystem.mapper.UserMapper;
import com.system.SmallBusinessBookingSystem.service.login.LoginService;
import com.system.SmallBusinessBookingSystem.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final LoginService loginService;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthTokenDto> signUp(@RequestBody UserRegistrationDto userRegistrationDto) {

        String token = userService.registerUser(userMapper.toUser(userRegistrationDto));

        return new ResponseEntity<>(new AuthTokenDto(token), HttpStatus.OK);

    }

    @PostMapping("/login")
    public ResponseEntity<AuthTokenDto> login(@RequestBody AuthCredentialsDto authCredentialsDto) {

        String token = loginService.login(authCredentialsDto.getEmail(), authCredentialsDto.getPassword());

        return new ResponseEntity<>(new AuthTokenDto(token), HttpStatus.OK);
    }
}