package com.sbz.appa.application.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String role;

    @Size(max = 50, message = "document is too long")
    private String document;

    @NotBlank(message = "a name is required")
    @Size(max = 50, message = "name is too long")
    private String name;

    @NotBlank(message = "a email is required")
    @Email(message = "email is invalid")
    @Size(max = 50, message = "email is too long")
    private String email;

    @Size(max = 25, message = "phone is too long")
    private String phone;

    @Size(max = 10, message = "vehicle is too long")
    private String vehicle;

    private Boolean available;
}
