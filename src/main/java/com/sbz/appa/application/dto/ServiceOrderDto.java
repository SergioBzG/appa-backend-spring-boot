package com.sbz.appa.application.dto;


import com.sbz.appa.application.validator.annotation.ValidCheckpoint;
import com.sbz.appa.application.validator.annotation.ValidServiceType;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceOrderDto {
    @NotBlank(message = "a type is required")
    @Size(max = 20, message = "type is too long")
    @ValidServiceType
    private String type;

    @NotBlank(message = "an origin checkpoint is required")
    @Size(max = 50, message = "origin checkpoint is too long")
    @ValidCheckpoint
    private String originCheckpoint;

    @NotBlank(message = "a destination checkpoint is required")
    @Size(max = 50, message = "destination checkpoint is too long")
    @ValidCheckpoint
    private String destinationCheckpoint;

    @Range(min = 1, max = 1000, message = "length must be between 1 and 1000")
    @Digits(integer = 4, fraction = 0, message = "invalid length")
    private Integer length;

    @Range(min = 1, max = 1000, message = "width must be between 1 and 1000")
    @Digits(integer = 4, fraction = 0, message = "invalid width")
    private Integer width;

    @Range(min = 1, max = 1000, message = "height mut be between 1 and 1000")
    @Digits(integer = 4, fraction = 0, message = "invalid height")
    private Integer height;

    @Range(min = 1, max = 1000, message = "weight must be between 1 and 1000")
    @Digits(integer = 4, fraction = 0, message = "invalid weight")
    private Integer weight;
}
