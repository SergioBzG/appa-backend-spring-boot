package com.sbz.appa.infrastructure.persistence.entity;


import com.sbz.appa.commons.Checkpoint;
import com.sbz.appa.commons.Nation;
import com.sbz.appa.commons.ServiceType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "services")
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = UserEntity.class, cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_citizen", referencedColumnName = "id")
    private UserEntity  userCitizen;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_bison", referencedColumnName = "id")
    private UserEntity  userBison;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "a type is required")
    private ServiceType type;

    @Column(nullable = false)
    @CreationTimestamp
    private Instant created;

    private LocalDateTime arrived;

    @Column(nullable = false)
    @NotNull(message = "a price is required")
    private Double price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "an origin nation is required")
    private Nation originNation;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "an destination nation is required")
    private Nation destinationNation;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "an origin checkpoint is required")
    private Checkpoint originCheckpoint;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "an destination checkpoint is required")
    private Checkpoint destinationCheckpoint;

    @OneToOne(mappedBy = "service", fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn // which indicates that the primary key of the Service entity is used as the foreign key value for the associated Carriage entity
    private CarriageEntity carriageEntity;

    @OneToOne(mappedBy = "service", fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn // which indicates that the primary key of the Service entity is used as the foreign key value for the associated Package entity
    private PackageEntity packageEntity;

    @OneToOne(mappedBy = "service", fetch = FetchType.EAGER)
    private GuideEntity guide;
}
