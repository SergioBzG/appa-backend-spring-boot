package com.sbz.appa.infrastructure.persistence.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.UniqueElements;

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

    @Column(length = 50)
    @NotEmpty(message = "invalid document")
    @Size(max = 50, message = "document is too long")
    private String document;

    @Column(nullable = false, unique = true, length = 50)
    @NotBlank(message = "a name is required")
    @Size(max = 50, message = "name is too long")
    private String name;

    @Column(nullable = false, unique = true, length = 50)
    @NotBlank(message = "a email is required")
    @Size(max = 50, message = "email is too long")
    private String email;

    @Column(nullable = false, length = 500)
    @NotBlank(message = "a password is required")
    @Size(max = 50)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(unique = true, length = 25)
    @NotEmpty(message = "invalid phone")
    @Size(max = 25, message = "phone is too long")
    private String phone;

    @Column(unique = true, length = 10)
    @NotEmpty(message = "invalid vehicle")
    @Size(max = 10, message = "vehicle is too long")
    private String vehicle;

    @Column(nullable = false, columnDefinition = "boolean default true")
    @NotNull(message = "available field is required")
//    @ColumnDefault(value = "0")
    private Boolean available;

    // TODO : create fields that represent relationship with other entity
    // RELATIONSHIPS
}
