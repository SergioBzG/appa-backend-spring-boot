package com.sbz.appa.application.dto;


import com.sbz.appa.application.validator.annotation.ValidCheckpoint;
import com.sbz.appa.application.validator.annotation.ValidNation;
import com.sbz.appa.application.validator.annotation.ValidServiceType;
import jakarta.validation.Valid;
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
    private Long id;

    private Long  userCitizen;

    private Long  userBison;

    @NotBlank(message = "a type is required")
    @Size(max = 20, message = "type is too long")
    @ValidServiceType
    private String type;

    private LocalDateTime created;

    private LocalDateTime arrived;

    @NotNull(message = "a price is required")
    @Digits(integer = 8, fraction = 5, message = "invalid price")
    private Double price;

    @NotBlank(message = "an origin nation is required")
    @Size(max = 50, message = "origin nation is too long")
    @ValidNation
    private String originNation;

    @NotBlank(message = "a destination nation is required")
    @Size(max = 50, message = "destination nation is too long")
    @ValidNation
    private String destinationNation;

    @NotBlank(message = "an origin checkpoint is required")
    @Size(max = 50, message = "origin checkpoint is too long")
    @ValidCheckpoint
    private String originCheckpoint;

    @NotBlank(message = "a destination checkpoint is required")
    @Size(max = 50, message = "destination checkpoint is too long")
    @ValidCheckpoint
    private String destinationCheckpoint;

    @Valid
    private CarriageDto carriageDto;

    @Valid
    private PackageDto packageDto;

    private GuideDto guide;
}
