package com.sbz.appa.util;

import com.sbz.appa.application.dto.CarriageDto;
import com.sbz.appa.application.dto.GuideDto;
import com.sbz.appa.application.dto.PackageDto;
import com.sbz.appa.application.dto.ServiceDto;

import java.time.LocalDateTime;
import java.util.UUID;

public class ServiceDtoTestData {

    public static ServiceDto createTestServiceDtoCarriage() {
        return ServiceDto.builder()
                .userCitizen(8L)
                .userBison(2L)
                .type("CARRIAGE")
                .created(LocalDateTime.parse("2024-07-29T13:43:50.75375"))
                .arrived(LocalDateTime.parse("2024-07-29T16:45:31.780712"))
                .price(674504.78)
                .originNation("WATER")
                .destinationNation("WATER")
                .originCheckpoint("NORTHERN_WATER")
                .destinationCheckpoint("SOUTHERN_WATER")
                .carriageDto(
                        CarriageDto.builder()
                                .pickUp(LocalDateTime.parse("2024-08-01T17:15:00"))
                                .description("I need it as soon as possible")
                                .build()
                )
                .guide(
                        GuideDto.builder()
                                .id(UUID.randomUUID())
                                .currentNation("WATER")
                                .currentCheckpoint("SOUTHERN_WATER")
                                .build()
                )
                .build();
    }

    public static ServiceDto createTestServiceDtoPackage() {
        return ServiceDto.builder()
                .userCitizen(4L)
                .userBison(2L)
                .type("PACKAGE")
                .created(LocalDateTime.parse("2024-05-20T16:00:13.699803"))
                .arrived(LocalDateTime.parse("2024-07-29T16:37:12.406007"))
                .price(454567.0)
                .originNation("FIRE")
                .destinationNation("AIR")
                .originCheckpoint("FIRE_CAPITAL")
                .destinationCheckpoint("NORTHERN_AIR")
                .packageDto(
                        PackageDto.builder()
                                .length(450)
                                .width(700)
                                .height(900)
                                .weight(1000)
                                .build()
                )
                .guide(
                        GuideDto.builder()
                                .id(UUID.randomUUID())
                                .currentNation("AIR")
                                .currentCheckpoint("NORTHERN_AIR")
                                .build()
                )
                .build();
    }
}

