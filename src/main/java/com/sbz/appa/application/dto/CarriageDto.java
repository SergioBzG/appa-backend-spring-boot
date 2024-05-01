package com.sbz.appa.application.dto;


import jakarta.validation.constraints.Future;
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
public class CarriageDto {
    @NotNull(message = "a pick up date is required")
    @Future(message = "invalid pick up date")
    private LocalDateTime pickUp;

    @NotBlank(message = "a description is required")
    @Size(max = 200, message = "description is too long")
    private String description;
}
