package com.system.SmallBusinessBookingSystem.service.domain;

import lombok.*;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@Builder
@ToString
public class Booking {
    private String id;
    private String userId;
    private String serviceId;
    private Instant dateTime;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;
}
