package com.sbz.appa.util;

import com.sbz.appa.application.dto.RouteDto;

import java.util.List;

public class RouteDtoTestData {

    public static RouteDto createTestRouteDto() {
        return RouteDto.builder()
                .optimalRoute(List.of("NORTHERN_WATER", "SOUTHERN_WATER", "NORTHERN_AIR", "SOUTHERN_AIR"))
                .build();
    }
}
