package com.system.SmallBusinessBookingSystem.controller;

import com.system.SmallBusinessBookingSystem.controller.dto.PasswordChangeDto;
import com.system.SmallBusinessBookingSystem.controller.dto.PasswordResetCompleteDto;
import com.system.SmallBusinessBookingSystem.controller.dto.PasswordResetRequestsDto;
import com.system.SmallBusinessBookingSystem.controller.dto.UserDetailsDto;
import com.system.SmallBusinessBookingSystem.mapper.UserMapper;
import com.system.SmallBusinessBookingSystem.service.models.User;
import com.system.SmallBusinessBookingSystem.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users/passwords")
@RequiredArgsConstructor
public class PasswordsController {

    private final UserService userService;
    private final UserMapper userMapper;

    // PUT /users/passwords  (зміна власного пароля)
    @PutMapping
    public ResponseEntity<Void> changeOwnPassword(@RequestBody PasswordChangeDto dto) {
        userService.changeOwnPassword(dto.getOldPassword(), dto.getNewPassword());
        return ResponseEntity.ok().build();
    }

    // PUT /users/passwords/reset  (запит на reset через email)
    @PutMapping("/reset")
    public ResponseEntity<Void> requestPasswordReset(@RequestBody PasswordResetRequestsDto dto) {
        userService.requestPasswordReset(dto.getEmail());
        return ResponseEntity.ok().build();
    }

    // GET /users/passwords/reset-verify?token=...  (верифікація токена)
    @GetMapping("/reset-verify")
    public ResponseEntity<UserDetailsDto> verifyResetToken(@RequestParam("token") String token) {
        User user = userService.verifyPasswordResetToken(token);
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toUserDetailsDto(user));
    }

    // POST /users/passwords/reset-complete  (встановити новий пароль за токеном)
    @PostMapping("/reset-complete")
    public ResponseEntity<Void> completeReset(@RequestBody PasswordResetCompleteDto dto) {
        userService.completePasswordReset(dto.getToken(), dto.getPassword());
        return ResponseEntity.ok().build();
    }

    // PUT /users/{id}/password  (адмін встановлює пароль іншому користувачу)
    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> adminSetPassword(@PathVariable String id, @RequestBody PasswordResetCompleteDto dto) {
        userService.adminSetPassword(id, dto.getPassword());
        return ResponseEntity.ok().build();
    }
}