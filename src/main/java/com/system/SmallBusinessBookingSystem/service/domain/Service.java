package com.system.SmallBusinessBookingSystem.service.domain;

import lombok.*;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@Builder
@ToString
public class Service {
    private String id;
    private String serviceName;
    private int duration;
    private Double price;
    private Instant createdAt;
    private Instant updatedAt;
}
