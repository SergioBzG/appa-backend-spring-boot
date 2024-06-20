package com.sbz.appa.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PathDto {
    @NotBlank(message = "an origin checkpoint is required")
    @Size(max = 50, message = "origin checkpoint is too long")
    private String originCheckpoint;

    @NotBlank(message = "an destination checkpoint is required")
    @Size(max = 50, message = "destination checkpoint is too long")
    private String destinationCheckpoint;
}
