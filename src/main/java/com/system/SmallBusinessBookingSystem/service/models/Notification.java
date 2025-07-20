package com.system.SmallBusinessBookingSystem.service.models;

import lombok.*;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@Builder
@ToString
public class Notification {
    private String id;
    private String message;
    private Instant sentAt;
    private String type;
    private Instant createdAt;
    private Instant updatedAt;
}