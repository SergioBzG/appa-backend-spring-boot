package com.sbz.appa.core.usecase;

import com.sbz.appa.application.dto.GuideDto;
import com.sbz.appa.application.dto.PathDto;
import com.sbz.appa.application.dto.RouteDto;
import com.sbz.appa.application.dto.ServiceDto;

import java.util.UUID;

public interface ServiceUseCase {
    ServiceDto saveService(ServiceDto serviceDto);

    ServiceDto updateService(Long id, GuideDto newLocation);

    ServiceDto getService(Long id);

    Double getServicePrice(PathDto pathDto);

    RouteDto getOptimalRoute(PathDto pathDto);

    ServiceDto getActiveService(String email);

    GuideDto trackService(UUID guideId);
}
