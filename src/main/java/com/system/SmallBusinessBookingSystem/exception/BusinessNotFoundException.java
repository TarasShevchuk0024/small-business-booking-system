package com.system.SmallBusinessBookingSystem.exception;

public class BusinessNotFoundException extends RuntimeException {
    public BusinessNotFoundException(String message) {
        super(message);
    }
}
