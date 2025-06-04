package com.system.SmallBusinessBookingSystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        String message = "Server is running";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}





