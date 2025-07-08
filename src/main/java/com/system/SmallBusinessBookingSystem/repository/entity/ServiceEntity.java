package com.system.SmallBusinessBookingSystem.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "services")
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "service_name")
    private String serviceName;

    @Column
    private String description;

    @Column
    private int duration;

    @Column
    private Double price;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "business_id")
    private BusinessEntity business;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    private List<BookingEntity> bookings;
}
