package com.sbz.appa.core.usecase;

import com.sbz.appa.application.dto.GuideDto;
import com.sbz.appa.application.dto.PathDto;
import com.sbz.appa.application.dto.RouteDto;
import com.sbz.appa.application.dto.ServiceDto;
import com.sbz.appa.infrastructure.persistence.entity.ServiceEntity;
import com.sbz.appa.infrastructure.persistence.entity.UserEntity;

import java.util.UUID;

public interface ServiceUseCase {
    ServiceDto saveService(ServiceDto serviceDto, String email);

    ServiceDto updateService(Long id, GuideDto newLocation, String email, Double price);

    ServiceDto getService(Long id, String email);

    Double getServicePrice(PathDto pathDto);

    RouteDto getOptimalRoute(PathDto pathDto);

    GuideDto trackService(UUID guideId);

    void searchForOrder(UserEntity userBison);

    void searchForBison(ServiceEntity serviceEntity);
}
