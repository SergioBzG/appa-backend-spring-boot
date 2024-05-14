package com.sbz.appa.core.usecase.impl;

import com.sbz.appa.application.dto.GuideDto;
import com.sbz.appa.application.dto.PathDto;
import com.sbz.appa.application.dto.RouteDto;
import com.sbz.appa.application.dto.ServiceDto;
import com.sbz.appa.core.mapper.Mapper;
import com.sbz.appa.core.usecase.ServiceUseCase;
import com.sbz.appa.infrastructure.persistence.entity.*;
import com.sbz.appa.infrastructure.persistence.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ServiceUseCaseImpl implements ServiceUseCase {

    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final Mapper<ServiceEntity, ServiceDto> serviceMapper;

    @Override
    public ServiceDto saveService(ServiceDto serviceDto, String email) {
        UserEntity userCitizen = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Citizen not found"));
        // Set guide
        serviceDto.setGuide(GuideDto.builder()
                .currentNation(serviceDto.getOriginNation())
                .currentCheckpoint(serviceDto.getOriginCheckpoint())
                .build()
        );
        ServiceEntity serviceEntity = serviceMapper.mapFrom(serviceDto);
        // Set userCitizen
        serviceEntity.setUserCitizen(userCitizen);
        // TODO : logic to looking for a bison for this service
        // serviceEntity.setUserBison();

        log.info("Saving service : {}", serviceEntity);
        serviceEntity.getPackageEntity().setService(serviceEntity); // Persist
        serviceEntity.getGuide().setService(serviceEntity); // Persist
        ServiceEntity savedService = serviceRepository.save(serviceEntity);

        return serviceMapper.mapTo(savedService);
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
