package com.sbz.appa.application.dto;


import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackageDto {
    @NotNull(message = "a length is required")
    @Range(min = 1, max = 1000, message = "length must be between 1 and 1000")
    @Digits(integer = 4, fraction = 0, message = "invalid length")
    private Integer length;

    @NotNull(message = "a width is required")
    @Range(min = 1, max = 1000, message = "width must be between 1 and 1000")
    @Digits(integer = 4, fraction = 0, message = "invalid width")
    private Integer width;

    @NotNull(message = "a height is required")
    @Range(min = 1, max = 1000, message = "height mut be between 1 and 1000")
    @Digits(integer = 4, fraction = 0, message = "invalid height")
    private Integer height;

    @NotNull(message = "a weight is required")
    @Range(min = 1, max = 1000, message = "weight must be between 1 and 1000")
    @Digits(integer = 4, fraction = 0, message = "invalid weight")
    private Integer weight;
}
