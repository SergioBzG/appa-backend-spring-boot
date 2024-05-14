package com.sbz.appa.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @OneToOne(targetEntity = ServiceEntity.class, fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

    @NotNull(message = "a pick up date is required")
    private LocalDateTime pickUp;

    @Column(nullable = false)
    @NotBlank(message = "a description is required")
    @Size(max = 200, message = "description is too long")
    private String description;

    @Override
    public String toString() {
        return "CarriageEntity{" +
                "pickUp=" + pickUp +
                ", description='" + description + '\'' +
                '}';
    }
}
