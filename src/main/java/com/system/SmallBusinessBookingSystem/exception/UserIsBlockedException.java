package com.system.SmallBusinessBookingSystem.exception;

public class UserIsBlockedException extends RuntimeException {
    public UserIsBlockedException(String message) {
        super(message);
    }
}
