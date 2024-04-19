package com.sbz.appa.infrastructure.persistence.entity;

import com.sbz.appa.infrastructure.persistence.utils.Checkpoint;
import com.sbz.appa.infrastructure.persistence.utils.Nation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "guides")
public class GuideEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(targetEntity = ServiceEntity.class, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "service_id")
    @NotNull(message = "a service is required")
    private ServiceEntity service;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "an current nation is required")
    private Nation currentNation;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "an current checkpoint is required")
    private Checkpoint currentCheckpoint;

}
