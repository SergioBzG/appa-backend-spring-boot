package com.sbz.appa.core.mapper;

import com.sbz.appa.application.dto.ServiceOrderDto;
import com.sbz.appa.commons.Checkpoint;
import com.sbz.appa.commons.ServiceType;
import com.sbz.appa.core.domain.model.CarriageOrder;
import com.sbz.appa.core.domain.model.PackageOrder;
import com.sbz.appa.core.domain.model.ServiceOrder;
import org.springframework.stereotype.Component;


@Component
public class ServiceOrderDtoToServiceOrder {

    public ServiceOrder mapFromDto(ServiceOrderDto serviceOrderDto) {
        if (serviceOrderDto.getType().equals(ServiceType.CARRIAGE.name()))
            return getCarriageOrder(serviceOrderDto);
        return getPackageOrder(serviceOrderDto);
    }

    private ServiceOrder getCarriageOrder(ServiceOrderDto serviceOrderDto) {
        return new CarriageOrder(
                ServiceType.valueOf(serviceOrderDto.getType()),
                Checkpoint.valueOf(serviceOrderDto.getOriginCheckpoint()),
                Checkpoint.valueOf(serviceOrderDto.getDestinationCheckpoint())
        );
    }

    private ServiceOrder getPackageOrder(ServiceOrderDto serviceOrderDto) {
        return new PackageOrder(
                ServiceType.valueOf(serviceOrderDto.getType()),
                Checkpoint.valueOf(serviceOrderDto.getOriginCheckpoint()),
                Checkpoint.valueOf(serviceOrderDto.getDestinationCheckpoint()),
                serviceOrderDto.getLength(),
                serviceOrderDto.getWidth(),
                serviceOrderDto.getHeight(),
                serviceOrderDto.getWeight()
        );
    }
}
