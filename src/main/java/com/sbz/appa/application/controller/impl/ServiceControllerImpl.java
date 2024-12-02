package com.sbz.appa.application.controller.impl;


import com.sbz.appa.application.controller.ServiceController;
import com.sbz.appa.application.dto.ServiceDto;
import com.sbz.appa.application.dto.GuideDto;
import com.sbz.appa.application.dto.ServiceOrderDto;
import com.sbz.appa.application.dto.PathDto;
import com.sbz.appa.application.dto.RouteDto;
import com.sbz.appa.application.util.ServicePrice;
import com.sbz.appa.application.validator.Validator;
import com.sbz.appa.core.usecase.ServiceUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.UUID;


@AllArgsConstructor
public class ServiceControllerImpl implements ServiceController {

    private final ServiceUseCase serviceUseCase;
    private final Validator<ServiceOrderDto> serviceOrderDtoValidator;
    private final Validator<ServiceDto> serviceDtoValidator;

    public ResponseEntity<ServiceDto> createService(ServiceDto serviceDto, Authentication authentication) {
        serviceDtoValidator.validate(serviceDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(serviceUseCase.saveService(serviceDto, authentication.getName()));
    }

    public ResponseEntity<ServiceDto> updateService(
            Long id,
            GuideDto newLocation,
            Double price,
            Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serviceUseCase.updateService(id, newLocation, authentication.getName(), price));
    }

    public ResponseEntity<ServiceDto> getService(Long id, Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serviceUseCase.getService(id, authentication.getName()));
    }

    public ResponseEntity<ServicePrice> getPrice(ServiceOrderDto serviceOrderDto) {
        serviceOrderDtoValidator.validate(serviceOrderDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ServicePrice(serviceUseCase.getServicePrice(serviceOrderDto)));
    }

    public ResponseEntity<RouteDto> getRoute(PathDto pathDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serviceUseCase.getOptimalRoute(pathDto));
    }

    public ResponseEntity<GuideDto> trackService(String guideId, Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serviceUseCase.trackService(UUID.fromString(guideId), authentication.getName()));
    }
}
