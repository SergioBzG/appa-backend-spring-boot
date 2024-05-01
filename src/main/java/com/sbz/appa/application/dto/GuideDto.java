package com.sbz.appa.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuideDto {
    private UUID id;

    @NotBlank(message = "an current nation is required")
    @Size(max = 50, message = "current nation is too long")
    private String currentNation;

    @NotBlank(message = "an current checkpoint is required")
    @Size(max = 50, message = "current checkpoint is too long")
    private String currentCheckpoint;
}
