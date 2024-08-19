package com.sbz.appa.util;

import com.sbz.appa.infrastructure.persistence.entity.CarriageEntity;

import java.time.LocalDateTime;

public class CarriageEntityTestData {

    public static CarriageEntity createTestCarriageEntity() {
        return CarriageEntity.builder()
                .serviceId(1L)
                .pickUp(LocalDateTime.parse("2024-08-01T17:15:00"))
                .description("I need it as soon as possible")
                .build();
    }
}
