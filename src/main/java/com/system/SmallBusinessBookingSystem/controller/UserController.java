package com.system.SmallBusinessBookingSystem.controller;

import com.system.SmallBusinessBookingSystem.controller.dto.UserDetailsDto;
import com.system.SmallBusinessBookingSystem.controller.dto.UserRegistrationDto;
import com.system.SmallBusinessBookingSystem.controller.dto.UserUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public ResponseEntity<List<UserDetailsDto>> getUsers() {
        List<UserDetailsDto> usersDto = new ArrayList<>();
        UserDetailsDto taras = new UserDetailsDto(
                "Taras", "Shevchuk", "taras.shevchuk0024@gmail.com",
                "+380633570183", "ADMIN", "ACTIVE");
        usersDto.add(taras);
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDto> getUsers(@PathVariable String id) {
        log.info("Get user with id {}", id);
        UserDetailsDto taras = new UserDetailsDto(
                "Taras", "Shevchuk", "taras.shevchuk0024@gmail.com",
                "+380633570183", "ADMIN", "ACTIVE");
        return new ResponseEntity<>(taras, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        log.info("Creating new user");
        log.info("User created: {}", userRegistrationDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody UserUpdateDto userUpdateDto) {
        log.info("Updating user");
        log.info("User updating: {}", userUpdateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        log.info("Deleting user with id: {}", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
