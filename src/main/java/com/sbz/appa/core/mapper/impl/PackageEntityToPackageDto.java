package com.sbz.appa.core.mapper.impl;

import com.sbz.appa.application.dto.PackageDto;
import com.sbz.appa.core.mapper.Mapper;
import com.sbz.appa.infrastructure.persistence.entity.PackageEntity;

public class PackageEntityToPackageDto implements Mapper<PackageEntity, PackageDto> {
    @Override
    public PackageDto mapTo(PackageEntity packageEntity) {
        return PackageDto.builder()
                .length(packageEntity.getLength())
                .width(packageEntity.getWidth())
                .height(packageEntity.getHeight())
                .weight(packageEntity.getWeight())
                .build();
    }

    @Override
    public PackageEntity mapFrom(PackageDto packageDto) {
        return null;
    }
}
