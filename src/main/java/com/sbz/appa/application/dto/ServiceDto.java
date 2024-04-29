package com.sbz.appa.application.dto;


import jakarta.validation.constraints.Digits;
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
public class ServiceDto {
    private UserDto  userCitizen;

    private UserDto  userBison;

    @NotBlank(message = "a type is required")
    @Size(max = 20, message = "type is too long")
    private String type;

    private LocalDateTime created;

    private LocalDateTime arrived;

    @NotNull(message = "a price is required")
    @Digits(integer = 6, fraction = 5, message = "Invalid price")
    private Double price;

    @NotBlank(message = "an origin nation is required")
    @Size(max = 50, message = "origin nation is too long")
    private String originNation;

    @NotBlank(message = "an destination nation is required")
    @Size(max = 50, message = "destination nation is too long")
    private String destinationNation;

    @NotBlank(message = "an origin checkpoint is required")
    @Size(max = 50, message = "origin checkpoint is too long")
    private String originCheckpoint;

    @NotBlank(message = "an destination checkpoint is required")
    @Size(max = 50, message = "destination checkpoint is too long")
    private String destinationCheckpoint;

    private CarriageDto carriageEntity;

    private PackageDto packageEntity;

    private GuideDto guide;
}
