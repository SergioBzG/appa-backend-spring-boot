package com.sbz.appa.util;

import com.sbz.appa.application.dto.ServiceOrderDto;

public class ServiceOrderDtoTestData {

    public static ServiceOrderDto createTestServiceOrderDto() {
        return ServiceOrderDto.builder()
                .type("CARRIAGE")
                .originCheckpoint("NORTHERN_WATER")
                .destinationCheckpoint("SOUTHERN_WATER")
                .length(450)
                .width(700)
                .height(900)
                .weight(1000)
                .build();
    }
}
