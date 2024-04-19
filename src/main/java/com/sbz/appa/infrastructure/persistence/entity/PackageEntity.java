package com.sbz.appa.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "packages")
public class PackageEntity {
    @Id
    private Long serviceId;

    @OneToOne(targetEntity = ServiceEntity.class, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

    @Column(nullable = false)
    @NotNull(message = "a length is required")
    @Range(min = 1, max = 1000)
    private Integer length;

    @Column(nullable = false)
    @NotNull(message = "a width is required")
    @Range(min = 1, max = 1000)
    private Integer width;

    @Column(nullable = false)
    @NotNull(message = "a height is required")
    @Range(min = 1, max = 1000)
    private Integer height;

    @Column(nullable = false)
    @NotNull(message = "a weight is required")
    @Range(min = 1, max = 1000)
    private Integer weight;
}
