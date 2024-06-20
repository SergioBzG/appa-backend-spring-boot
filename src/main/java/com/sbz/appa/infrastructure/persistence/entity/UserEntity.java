package com.sbz.appa.infrastructure.persistence.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = RoleEntity.class)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private RoleEntity role;

    @Column(unique = true)
    @Size(max = 50, message = "document is too long")
    private String document;

    @Column(nullable = false)
    @NotBlank(message = "a name is required")
    @Size(max = 50, message = "name is too long")
    private String name;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "a email is required")
    @Size(max = 50, message = "email is too long")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "a password is required")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(unique = true)
    @Size(max = 25, message = "phone is too long")
    private String phone;

    @Column(unique = true)
    @Size(max = 10, message = "vehicle is too long")
    private String vehicle;

    @Column(columnDefinition = "boolean default true")
    @NotNull(message = "available field is required")
    private Boolean available;

    private LocalDateTime lastDelivery;

    @OneToMany(mappedBy = "userCitizen", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<ServiceEntity> citizenOrders;

    @OneToMany(mappedBy = "userBison", fetch = FetchType.LAZY)
    private List<ServiceEntity> bisonOrders;

    @PrePersist
    void preInsert() {
        if (this.available == null) this.available = true;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", role=" + role +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
