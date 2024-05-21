package com.sbz.appa.core.usecase.impl;

import com.sbz.appa.application.dto.GuideDto;
import com.sbz.appa.application.dto.PathDto;
import com.sbz.appa.application.dto.RouteDto;
import com.sbz.appa.application.dto.ServiceDto;
import com.sbz.appa.commons.ServiceType;
import com.sbz.appa.core.mapper.Mapper;
import com.sbz.appa.core.usecase.ServiceUseCase;
import com.sbz.appa.infrastructure.persistence.entity.*;
import com.sbz.appa.infrastructure.persistence.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ServiceUseCaseImpl implements ServiceUseCase {

    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final Mapper<ServiceEntity, ServiceDto> serviceMapper;
    private final Mapper<GuideEntity, GuideDto> guideMapper;

    @Override
    public ServiceDto saveService(ServiceDto serviceDto, String email) {
        log.info("Service dto {}", serviceDto);
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
        // Set up package or carriage
        if (serviceEntity.getPackageEntity() != null)
            serviceEntity.getPackageEntity().setService(serviceEntity); // Persist
        else
            serviceEntity.getCarriageEntity().setService(serviceEntity); // Persist
        serviceEntity.getGuide().setService(serviceEntity); // Persist
        ServiceEntity savedService = serviceRepository.save(serviceEntity);

        return serviceMapper.mapTo(savedService);
    }

    @Transactional
    @Override
    public ServiceDto updateService(Long id, GuideDto newLocation, String email, Double price) {
        log.info("Service id {}", id);
        ServiceEntity serviceEntity = serviceRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Service not found"));

        GuideEntity newLocationEntity = guideMapper.mapFrom(newLocation);
        // Update service guide
        serviceEntity.getGuide().setCurrentNation(newLocationEntity.getCurrentNation());
        serviceEntity.getGuide().setCurrentCheckpoint(newLocationEntity.getCurrentCheckpoint());

        if (serviceEntity.getUserBison() == null || !serviceEntity.getUserBison().getEmail().equals(email))
            throw new IllegalStateException("You do not have a service assigned with this id");
        else if (serviceEntity.getArrived() != null)
            throw new IllegalStateException("Service has already arrived to its destination");
        else  if (newLocationEntity.getCurrentCheckpoint().equals(serviceEntity.getDestinationCheckpoint())) {
            // Package or Carriage has arrived to its destination
            if (serviceEntity.getType() == ServiceType.CARRIAGE && price == null)
                throw new IllegalStateException("Price is required for carriage service");
            else if (serviceEntity.getType() == ServiceType.CARRIAGE)
                serviceEntity.setPrice(price);

            serviceEntity.setArrived(LocalDateTime.now());
            // Release Bison and look for an available service for them
            serviceEntity.getUserBison().setAvailable(true);
            searchForOrder(serviceEntity.getUserBison());
        }

        return serviceMapper.mapTo(serviceEntity);
    }

    @Override
    public ServiceDto getService(Long id, String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        ServiceEntity serviceEntity = serviceRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Service not found"));

        if (user.getRole().getName().equals("ROLE_BISON") && (serviceEntity.getUserBison() == null || !serviceEntity.getUserBison().getEmail().equals(email)))
            throw new IllegalStateException("You do not have a service assigned with this id");
        else if (user.getRole().getName().equals("ROLE_CITIZEN") && !serviceEntity.getUserCitizen().getEmail().equals(email))
            throw new IllegalStateException("You do not have a service assigned with this id");

        return serviceMapper.mapTo(serviceEntity);
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

    @Transactional
    @Override
    public void searchForOrder(UserEntity userBison) {
        // Search for order
        serviceRepository.findFirstByArrivedIsNullAndUserBisonIsNullOrderByCreatedAsc()
                .ifPresent(service -> {
                    service.setUserBison(userBison);
                    userBison.setAvailable(false);
                });
    }
}
