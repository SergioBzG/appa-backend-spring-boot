package com.sbz.appa.core.usecase;

import com.sbz.appa.application.dto.*;
import com.sbz.appa.infrastructure.persistence.entity.ServiceEntity;
import com.sbz.appa.infrastructure.persistence.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface ServiceUseCase {
    ServiceDto saveService(ServiceDto serviceDto, String email);

    ServiceDto updateService(Long id, GuideDto newLocation, String email, Double price);

    ServiceDto getService(Long id, String email);

    Double getServicePrice(ServiceOrderDto serviceOrderDto);

    RouteDto getOptimalRoute(PathDto pathDto);

    GuideDto trackService(UUID guideId, String userEmail);

    Optional<ServiceDto> getActiveService(Long bisonId);

    void searchForOrder(UserEntity userBison);

    void searchForBison(ServiceEntity serviceEntity);
}
