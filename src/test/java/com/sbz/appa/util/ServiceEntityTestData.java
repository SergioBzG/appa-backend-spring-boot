package com.sbz.appa.util;

import com.sbz.appa.commons.Checkpoint;
import com.sbz.appa.commons.Nation;
import com.sbz.appa.commons.ServiceType;
import com.sbz.appa.infrastructure.persistence.entity.ServiceEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class ServiceEntityTestData {

    public static ServiceEntity createTestServiceEntityCarriage() {
        return ServiceEntity.builder()
                .id(1L)
                .type(ServiceType.CARRIAGE)
                .created(LocalDateTime.parse("2024-07-29T13:43:50.75375")
                        .atZone(ZoneId.of("America/Bogota")).toInstant())
                .arrived(LocalDateTime.parse("2024-07-29T16:45:31.780712"))
                .price(674504.78)
                .originNation(Nation.WATER)
                .destinationNation(Nation.WATER)
                .originCheckpoint(Checkpoint.NORTHERN_WATER)
                .destinationCheckpoint(Checkpoint.SOUTHERN_WATER)
                .carriageEntity(CarriageEntityTestData.createTestCarriageEntity())
                .guide(GuideEntityTestData.createTestGuideEntity())
                .build();
    }

    public static ServiceEntity createTestServiceEntityPackage() {
        return ServiceEntity.builder()
                .id(2L)
                .type(ServiceType.PACKAGE)
                .created(LocalDateTime.parse("2024-05-20T16:00:13.699803")
                        .atZone(ZoneId.of("America/Bogota")).toInstant())
                .arrived(LocalDateTime.parse("2024-07-29T16:37:12.406007"))
                .price(454567.0)
                .originNation(Nation.FIRE)
                .destinationNation(Nation.AIR)
                .originCheckpoint(Checkpoint.FIRE_CAPITAL)
                .destinationCheckpoint(Checkpoint.NORTHERN_AIR)
                .packageEntity(PackageEntityTestData.createTestPackageEntity())
                .guide(GuideEntityTestData.createTestGuideEntity())
                .build();
    }

    public static ServiceEntity createTestServiceEntityPackageWithBison() {
        return ServiceEntity.builder()
                .id(2L)
                .userBison(UserEntityTestData.createTestUserEntityBison())
                .type(ServiceType.PACKAGE)
                .created(LocalDateTime.parse("2024-05-20T16:00:13.699803")
                        .atZone(ZoneId.of("America/Bogota")).toInstant())
                .arrived(LocalDateTime.parse("2024-07-29T16:45:31.780712"))
                .price(454567.0)
                .originNation(Nation.FIRE)
                .destinationNation(Nation.AIR)
                .originCheckpoint(Checkpoint.FIRE_CAPITAL)
                .destinationCheckpoint(Checkpoint.NORTHERN_AIR)
                .packageEntity(PackageEntityTestData.createTestPackageEntity())
                .guide(GuideEntityTestData.createTestGuideEntity())
                .build();
    }

    public static ServiceEntity createTestServiceEntityCarriageWithBisonAndArrivedNull() {
        return ServiceEntity.builder()
                .id(1L)
                .userBison(UserEntityTestData.createTestUserEntityBison())
                .type(ServiceType.CARRIAGE)
                .created(LocalDateTime.parse("2024-07-29T13:43:50.75375")
                        .atZone(ZoneId.of("America/Bogota")).toInstant())
                .originNation(Nation.WATER)
                .destinationNation(Nation.WATER)
                .originCheckpoint(Checkpoint.NORTHERN_WATER)
                .destinationCheckpoint(Checkpoint.SOUTHERN_WATER)
                .carriageEntity(CarriageEntityTestData.createTestCarriageEntity())
                .guide(GuideEntityTestData.createTestGuideEntity())
                .build();
    }

    public static ServiceEntity createTestServiceEntityCarriageWithCitizen() {
        return ServiceEntity.builder()
                .id(1L)
                .userBison(UserEntityTestData.createTestUserEntityBison())
                .userCitizen(UserEntityTestData.createTestUserEntityCitizen())
                .type(ServiceType.CARRIAGE)
                .created(LocalDateTime.parse("2024-07-29T13:43:50.75375")
                        .atZone(ZoneId.of("America/Bogota")).toInstant())
                .originNation(Nation.WATER)
                .destinationNation(Nation.WATER)
                .originCheckpoint(Checkpoint.NORTHERN_WATER)
                .destinationCheckpoint(Checkpoint.SOUTHERN_WATER)
                .carriageEntity(CarriageEntityTestData.createTestCarriageEntity())
                .guide(GuideEntityTestData.createTestGuideEntity())
                .build();
    }
}
