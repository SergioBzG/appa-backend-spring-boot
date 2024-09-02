package com.sbz.appa.util;

import com.sbz.appa.application.dto.ServiceOrderDto;

public class ServiceOrderDtoTestData {

    public static ServiceOrderDto createTestServiceOrderDto() {
        return ServiceOrderDto.builder()
                .type("CARRIAGE")
                .originCheckpoint("NORTHERN_WATER")
                .destinationCheckpoint("SOUTHERN_AIR")
                .build();
    }
}
