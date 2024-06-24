package com.sbz.appa.application.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceOrderDto {
    @NotBlank(message = "a type is required")
    @Size(max = 20, message = "type is too long")
    private String type;

    @NotBlank(message = "an origin checkpoint is required")
    @Size(max = 50, message = "origin checkpoint is too long")
    private String originCheckpoint;

    @NotBlank(message = "a destination checkpoint is required")
    @Size(max = 50, message = "destination checkpoint is too long")
    private String destinationCheckpoint;

    private Integer length;
    private Integer width;
    private Integer height;
    private Integer weight;
}
