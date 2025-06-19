package com.system.SmallBusinessBookingSystem.service.models;

import lombok.*;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@Builder
@ToString
public class Business {
    private String id;
    private String businessName;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
}
