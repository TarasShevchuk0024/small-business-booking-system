package com.system.SmallBusinessBookingSystem.controller;

import com.system.SmallBusinessBookingSystem.controller.dto.UserDetailsDto;
import com.system.SmallBusinessBookingSystem.controller.dto.UserRegistrationDto;
import com.system.SmallBusinessBookingSystem.controller.dto.UserUpdateDto;
import com.system.SmallBusinessBookingSystem.mapper.UserMapper;
import com.system.SmallBusinessBookingSystem.service.models.User;
import com.system.SmallBusinessBookingSystem.service.models.UserType;
import com.system.SmallBusinessBookingSystem.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.system.SmallBusinessBookingSystem.service.models.UserType.ROLE_ADMIN;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserDetailsDto>> getUsers() {

        List<UserDetailsDto> userDetailsDtoList = userService.getAllUsers()
                .stream()
                .map(userMapper::toUserDetailsDto)
                .toList();

        return new ResponseEntity<>(userDetailsDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDto> getUsers(@PathVariable String id) {

        User user = userService.getUser(id);
        UserDetailsDto userDetailsDto = userMapper.toUserDetailsDto(user);

        return new ResponseEntity<>(userDetailsDto, HttpStatus.OK);
    }

    @PostMapping
    @Secured(UserType.UserTypeConstants.ADMIN)
    public ResponseEntity<Void> createUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        userService.createUser(userMapper.toUser(userRegistrationDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody UserUpdateDto userUpdateDto) {

        User authenticatedUser = userService.getAuthenticatedUser();

        if (authenticatedUser.getType() == ROLE_ADMIN ||
                authenticatedUser.getId().equals(userUpdateDto.getId())) {
            userService.updateUser(userMapper.toUser(userUpdateDto));
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/{id}")
    @Secured(UserType.UserTypeConstants.ADMIN)
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

























