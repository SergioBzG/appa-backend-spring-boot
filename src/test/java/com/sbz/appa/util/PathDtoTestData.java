package com.sbz.appa.util;

import com.sbz.appa.application.dto.PathDto;

public class PathDtoTestData {

    public static PathDto createTestPathDto() {
        return PathDto.builder()
                .originCheckpoint("NORTHERN_WATER")
                .destinationCheckpoint("SOUTHERN_WATER")
                .build();
    }
}
