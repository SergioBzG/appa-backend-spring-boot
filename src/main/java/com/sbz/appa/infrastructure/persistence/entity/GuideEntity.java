package com.sbz.appa.infrastructure.persistence.entity;

import com.sbz.appa.commons.Checkpoint;
import com.sbz.appa.commons.Nation;
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

    @OneToOne(targetEntity = ServiceEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "service_id")
    @NotNull(message = "a service is required")
    private ServiceEntity service;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "a current nation is required")
    private Nation currentNation;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "a current checkpoint is required")
    private Checkpoint currentCheckpoint;

    @Override
    public String toString() {
        return "GuideEntity{" +
                "id=" + id +
                ", currentNation=" + currentNation +
                ", currentCheckpoint=" + currentCheckpoint +
                '}';
    }
}
