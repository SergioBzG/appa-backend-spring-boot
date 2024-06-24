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

import java.time.Instant;
import java.time.ZoneId;

@Component
public class ServiceEntityToServiceDto implements Mapper<ServiceEntity, ServiceDto> {
    @Override
    public ServiceDto mapToDto(ServiceEntity serviceEntity) {
        return ServiceDto.builder()
                .id(serviceEntity.getId())
                .userCitizen(serviceEntity.getUserCitizen().getId())
                .userBison(
                        serviceEntity.getUserBison() == null ? null :
                                serviceEntity.getUserBison().getId()
                        )
                .type(serviceEntity.getType().name())
                .created(serviceEntity.getCreated().atZone(ZoneId.of("America/Bogota")).toLocalDateTime())
                .arrived(serviceEntity.getArrived())
                .price(serviceEntity.getPrice())
                .originNation(serviceEntity.getOriginNation().name())
                .destinationNation(serviceEntity.getDestinationNation().name())
                .originCheckpoint(serviceEntity.getOriginCheckpoint().name())
                .destinationCheckpoint(serviceEntity.getDestinationCheckpoint().name())
                .carriageDto(getCarriageDto(serviceEntity))
                .packageDto(getPackageDto(serviceEntity))
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
    public ServiceEntity mapFromDto(ServiceDto serviceDto) {
        return ServiceEntity.builder()
                .id(serviceDto.getId())
                .type(ServiceType.valueOf(serviceDto.getType()))
                .created(getCreatedDateTime(serviceDto))
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
        return serviceDto.getPackageDto() == null ? null :
                PackageEntity.builder()
                        .length(serviceDto.getPackageDto().getLength())
                        .width(serviceDto.getPackageDto().getWidth())
                        .height(serviceDto.getPackageDto().getHeight())
                        .weight(serviceDto.getPackageDto().getWeight())
                        .build();
    }

    private CarriageEntity getCarriageEntity(ServiceDto serviceDto) {
        return serviceDto.getCarriageDto() == null ? null :
                CarriageEntity.builder()
                        .pickUp(serviceDto.getCarriageDto().getPickUp())
                        .description(serviceDto.getCarriageDto().getDescription())
                        .build();
    }

    private Instant getCreatedDateTime(ServiceDto serviceDto) {
        return serviceDto.getCreated() == null ? null : serviceDto.getCreated().atZone(ZoneId.of("America/Bogota")).toInstant();
    }
}
