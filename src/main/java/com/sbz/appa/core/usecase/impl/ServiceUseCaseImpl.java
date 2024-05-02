package com.sbz.appa.core.usecase.impl;

import com.sbz.appa.application.dto.GuideDto;
import com.sbz.appa.application.dto.PathDto;
import com.sbz.appa.application.dto.RouteDto;
import com.sbz.appa.application.dto.ServiceDto;
import com.sbz.appa.core.usecase.ServiceUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ServiceUseCaseImpl implements ServiceUseCase {


    @Override
    public ServiceDto saveService(ServiceDto serviceDto) {
        return null;
    }

    @Override
    public ServiceDto updateService(Long id, GuideDto newLocation) {
        return null;
    }

    @Override
    public ServiceDto getService(Long id) {
        return null;
    }

    @Override
    public Double getServicePrice(PathDto pathDto) {
        return 0.0;
    }

    @Override
    public RouteDto getOptimalRoute(PathDto pathDto) {
        return null;
    }

    @Override
    public ServiceDto getActiveService(String email) {
        return null;
    }

    @Override
    public GuideDto trackService(UUID guideId) {
        return null;
    }
}
