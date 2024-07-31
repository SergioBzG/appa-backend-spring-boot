package com.sbz.appa.application.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;

    private String role;

    @Size(max = 50, message = "document is too long")
    @Pattern(regexp = "^(CC|CE|P)-\\d{3,17}$", message = "invalid document format e.g. CC|CE|P-12345")
    private String document;

    @NotBlank(message = "a name is required")
    @Size(max = 50, message = "name is too long")
    private String name;

    @NotBlank(message = "a email is required")
    @Email(message = "email is invalid")
    @Size(max = 50, message = "email is too long")
    private String email;

    @NotBlank(message = "a password is required")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Pattern(regexp = ".{8,}", message = "password has to contain at least 8 characters")
    private String password;

    @Size(max = 25, message = "phone is too long")
    @Pattern(regexp = "^\\d{7,10}$", message = "invalid phone format")
    private String phone;

    @Size(max = 10, message = "vehicle is too long")
    @Pattern(regexp = "^[A-Z]{3}-\\d{3}$", message = "invalid vehicle license plate e.g. AAA-123")
    private String vehicle;

    @Null(message = "available property can not be updated")
    private Boolean available;

    @Null(message = "lastDelivery property can not be updated")
    private LocalDateTime lastDelivery;
}
