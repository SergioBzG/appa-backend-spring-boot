package com.sbz.appa.util;

import com.sbz.appa.infrastructure.persistence.entity.PackageEntity;

public class PackageEntityTestData {

    public static PackageEntity createTestPackageEntity() {
        return PackageEntity.builder()
                .serviceId(2L)
                .length(450)
                .width(700)
                .height(900)
                .weight(100)
                .build();
    }
}
