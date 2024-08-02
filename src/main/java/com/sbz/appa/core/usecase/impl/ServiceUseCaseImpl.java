package com.sbz.appa.core.usecase.impl;

import com.sbz.appa.application.dto.*;
import com.sbz.appa.application.exception.ActionNotAllowedException;
import com.sbz.appa.application.exception.InvalidOrMissingDataException;
import com.sbz.appa.application.exception.NotFoundException;
import com.sbz.appa.commons.Role;
import com.sbz.appa.commons.Checkpoint;
import com.sbz.appa.commons.ServiceType;
import com.sbz.appa.core.domain.model.ServiceOrder;
import com.sbz.appa.core.mapper.Mapper;
import com.sbz.appa.core.mapper.ServiceOrderDtoToServiceOrder;
import com.sbz.appa.core.usecase.ServiceUseCase;
import com.sbz.appa.infrastructure.persistence.entity.*;
import com.sbz.appa.infrastructure.persistence.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ServiceUseCaseImpl implements ServiceUseCase {

    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final Mapper<ServiceEntity, ServiceDto> serviceMapper;
    private final Mapper<GuideEntity, GuideDto> guideMapper;
    private final ServiceOrderDtoToServiceOrder serviceOrderDtoToServiceOrder;

    @Override
    public ServiceDto saveService(ServiceDto serviceDto, String email) {
        log.info("Service dto {}", serviceDto);
        UserEntity userCitizen = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("user"));
        // Set guide
        serviceDto.setGuide(GuideDto.builder()
                .currentNation(serviceDto.getOriginNation())
                .currentCheckpoint(serviceDto.getOriginCheckpoint())
                .build()
        );
        ServiceEntity serviceEntity = serviceMapper.mapFromDto(serviceDto);
        // Set userCitizen
        serviceEntity.setUserCitizen(userCitizen);

        // Look for a bison for this service and set it
        searchForBison(serviceEntity);

        log.info("Saving service : {}", serviceEntity);
        // Set up package or carriage
        if (serviceEntity.getPackageEntity() != null)
            serviceEntity.getPackageEntity().setService(serviceEntity); // Persist
        else
            serviceEntity.getCarriageEntity().setService(serviceEntity); // Persist
        serviceEntity.getGuide().setService(serviceEntity); // Persist

        return serviceMapper.mapToDto(serviceRepository.save(serviceEntity));
    }

    @Transactional
    @Override
    public ServiceDto updateService(Long id, GuideDto newLocation, String email, Double price) {
        ServiceEntity serviceEntity = serviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("service"));

        GuideEntity newLocationEntity = guideMapper.mapFromDto(newLocation);
        // Update service guide
        serviceEntity.getGuide().setCurrentNation(newLocationEntity.getCurrentNation());
        serviceEntity.getGuide().setCurrentCheckpoint(newLocationEntity.getCurrentCheckpoint());

        if (serviceEntity.getUserBison() == null || !serviceEntity.getUserBison().getEmail().equals(email))
            throw new NotFoundException("service");
        else if (serviceEntity.getArrived() != null)
            throw new ActionNotAllowedException("service", "updating");
        else  if (newLocationEntity.getCurrentCheckpoint().equals(serviceEntity.getDestinationCheckpoint())) {
            // Package or Carriage has arrived to its destination
            if (serviceEntity.getType() == ServiceType.CARRIAGE && price == null)
                throw new InvalidOrMissingDataException("carriage", "price");
            else if (serviceEntity.getType() == ServiceType.CARRIAGE)
                serviceEntity.setPrice(price);

            serviceEntity.setArrived(LocalDateTime.now());
            // Release Bison and look for an available service for them
            UserEntity bison = serviceEntity.getUserBison();
            bison.setAvailable(true);
            bison.setLastDelivery(LocalDateTime.now());
            searchForOrder(bison);
        }

        return serviceMapper.mapToDto(serviceEntity);
    }

    @Override
    public ServiceDto getService(Long id, String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("user"));
        ServiceEntity serviceEntity = serviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("service"));

        if (user.getRole().getName().equals(Role.ROLE_BISON.name())
                && (serviceEntity.getUserBison() == null || !serviceEntity.getUserBison().getEmail().equals(email)))
            throw new NotFoundException("service");
        else if (user.getRole().getName().equals(Role.ROLE_CITIZEN.name())
                && !serviceEntity.getUserCitizen().getEmail().equals(email))
            throw new NotFoundException("service");

        return serviceMapper.mapToDto(serviceEntity);
    }

    @Override
    public Double getServicePrice(ServiceOrderDto serviceOrderDto) {
        ServiceOrder serviceOrder = serviceOrderDtoToServiceOrder.mapFromDto(serviceOrderDto);
        return serviceOrder.getPrice();
    }

    @Override
    public RouteDto getOptimalRoute(PathDto pathDto) {
        List<String> routeList = ServiceOrder.getPathList(
                Checkpoint.valueOf(pathDto.getOriginCheckpoint()),
                Checkpoint.valueOf(pathDto.getDestinationCheckpoint())
        );
        return RouteDto.builder()
                .optimalRoute(routeList)
                .build();
    }

    @Override
    public GuideDto trackService(UUID guideId, String userEmail) {
        ServiceEntity service = serviceRepository.findByGuideId(guideId)
                .orElseThrow(() -> new NotFoundException("service"));
        if (!service.getUserCitizen().getEmail().equals(userEmail))
            throw new NotFoundException("service");

        return guideMapper.mapToDto(service.getGuide());

    }

    @Override
    public Optional<ServiceDto> getActiveService(Long bisonId) {
        return serviceRepository.findFirstByArrivedIsNullAndUserBisonId(bisonId)
                .map(serviceMapper::mapToDto);
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

    @Transactional
    @Override
    public void searchForBison(ServiceEntity serviceEntity) {
        // First, look for a bison who has never had a service
        Optional<UserEntity> bisonWithoutOrders = userRepository
                .findFirstByAvailableIsTrueAndRoleNameAndLastDeliveryIsNull(Role.ROLE_BISON.name());
        if(bisonWithoutOrders.isPresent()){
            serviceEntity.setUserBison(bisonWithoutOrders.get());
            bisonWithoutOrders.get().setAvailable(false);
            return;
        }

        // If any, look for bison with more time available
        Optional<UserEntity> moreTimeAvailableBison = userRepository
                .findFirstByAvailableIsTrueAndRoleNameAndLastDeliveryIsNotNullOrderByLastDeliveryAsc(
                        Role.ROLE_BISON.name());
        if(moreTimeAvailableBison.isPresent()){
            serviceEntity.setUserBison(moreTimeAvailableBison.get());
            moreTimeAvailableBison.get().setAvailable(false);
        }
    }
}
