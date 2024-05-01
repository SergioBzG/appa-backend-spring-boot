package com.sbz.appa.core.mapper.impl;

import com.sbz.appa.application.dto.CarriageDto;
import com.sbz.appa.application.dto.GuideDto;
import com.sbz.appa.application.dto.PackageDto;
import com.sbz.appa.application.dto.ServiceDto;
import com.sbz.appa.commons.Checkpoint;
import com.sbz.appa.commons.Nation;
import com.sbz.appa.commons.ServiceType;
import com.sbz.appa.core.mapper.Mapper;
import com.sbz.appa.infrastructure.persistence.entity.CarriageEntity;
import com.sbz.appa.infrastructure.persistence.entity.GuideEntity;
import com.sbz.appa.infrastructure.persistence.entity.PackageEntity;
import com.sbz.appa.infrastructure.persistence.entity.ServiceEntity;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class ServiceEntityToServiceDto implements Mapper<ServiceEntity, ServiceDto> {
    @Override
    public ServiceDto mapTo(ServiceEntity serviceEntity) {
        return ServiceDto.builder()
                .userCitizen(serviceEntity.getUserCitizen().getId())
                .userBison(serviceEntity.getUserBison().getId())
                .type(serviceEntity.getType().name())
                .created(serviceEntity.getCreated().atZone(ZoneId.of("America/Bogota")).toLocalDateTime())
                .arrived(serviceEntity.getArrived())
                .price(serviceEntity.getPrice())
                .originNation(serviceEntity.getOriginNation().name())
                .destinationNation(serviceEntity.getDestinationNation().name())
                .originCheckpoint(serviceEntity.getOriginCheckpoint().name())
                .destinationCheckpoint(serviceEntity.getDestinationCheckpoint().name())
                .carriageEntity(getCarriageDto(serviceEntity))
                .packageEntity(getPackageDto(serviceEntity))
                .guide(
                        GuideDto.builder()
                                .id(serviceEntity.getGuide().getId())
                                .currentNation(serviceEntity.getGuide().getCurrentNation().name())
                                .currentCheckpoint(serviceEntity.getGuide().getCurrentCheckpoint().name())
                                .build()
                )
                .build();
    }

    @Override
    public ServiceEntity mapFrom(ServiceDto serviceDto) {
        return ServiceEntity.builder()
                .type(ServiceType.valueOf(serviceDto.getType()))
                .created(serviceDto.getCreated().atZone(ZoneId.of("America/Bogota")).toInstant())
                .arrived(serviceDto.getArrived())
                .price(serviceDto.getPrice())
                .originNation(Nation.valueOf(serviceDto.getOriginNation()))
                .destinationNation(Nation.valueOf(serviceDto.getDestinationNation()))
                .originCheckpoint(Checkpoint.valueOf(serviceDto.getOriginCheckpoint()))
                .destinationCheckpoint(Checkpoint.valueOf(serviceDto.getDestinationCheckpoint()))
                .carriageEntity(getCarriageEntity(serviceDto))
                .packageEntity(getPackageEntity(serviceDto))
                .guide(
                        GuideEntity.builder()
                                .id(serviceDto.getGuide().getId())
                                .currentNation(Nation.valueOf(serviceDto.getGuide().getCurrentNation()))
                                .currentCheckpoint(Checkpoint.valueOf(serviceDto.getGuide().getCurrentCheckpoint()))
                                .build()
                )
                .build();
    }

    private CarriageDto getCarriageDto(ServiceEntity serviceEntity) {
        return serviceEntity.getCarriageEntity() == null ? null :
                CarriageDto.builder()
                        .pickUp(serviceEntity.getCarriageEntity().getPickUp())
                        .description(serviceEntity.getCarriageEntity().getDescription())
                        .build();
    }

    private PackageDto getPackageDto(ServiceEntity serviceEntity) {
        return serviceEntity.getPackageEntity() == null ? null :
                PackageDto.builder()
                        .length(serviceEntity.getPackageEntity().getLength())
                        .width(serviceEntity.getPackageEntity().getWidth())
                        .height(serviceEntity.getPackageEntity().getHeight())
                        .weight(serviceEntity.getPackageEntity().getWeight())
                        .build();
    }

    private PackageEntity getPackageEntity(ServiceDto serviceDto) {
        return serviceDto.getPackageEntity() == null ? null :
                PackageEntity.builder()
                        .length(serviceDto.getPackageEntity().getLength())
                        .width(serviceDto.getPackageEntity().getWidth())
                        .height(serviceDto.getPackageEntity().getHeight())
                        .weight(serviceDto.getPackageEntity().getWeight())
                        .build();
    }

    private CarriageEntity getCarriageEntity(ServiceDto serviceDto) {
        return serviceDto.getCarriageEntity() == null ? null :
                CarriageEntity.builder()
                        .pickUp(serviceDto.getCarriageEntity().getPickUp())
                        .description(serviceDto.getCarriageEntity().getDescription())
                        .build();
    }
}
