package com.sbz.appa.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "carriages")
public class CarriageEntity {
    @Id
    private Long serviceId;

    @OneToOne(targetEntity = ServiceEntity.class, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

    @Column(nullable = false)
    @NotNull(message = "a pick up date is required")
    private LocalDateTime pickUp;

    @Column(nullable = false)
    @NotEmpty(message = "a description is required")
    @Size(max = 200, message = "description is too long")
    private String description;

}
