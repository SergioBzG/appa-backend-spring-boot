package com.sbz.appa.application.controller;


import com.sbz.appa.application.dto.*;
import com.sbz.appa.application.util.ServicePrice;
import com.sbz.appa.application.validator.Validator;
import com.sbz.appa.application.validator.annotation.ValidUUID;
import com.sbz.appa.core.usecase.ServiceUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/services")
@AllArgsConstructor
@Validated
@Slf4j
public class ServiceController {

    private final ServiceUseCase serviceUseCase;
    private final Validator<ServiceOrderDto> serviceOrderDtoValidator;
    private final Validator<ServiceDto> serviceDtoValidator;

    @PostMapping(value = "/create")
    public ResponseEntity<ServiceDto> createService(
            @RequestBody @Valid ServiceDto serviceDto,
            Authentication authentication) {
        serviceDtoValidator.validate(serviceDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(serviceUseCase.saveService(serviceDto, authentication.getName()));
    }

    @PatchMapping(value = "/update/{id}")
    public ResponseEntity<ServiceDto> updateService(
            @PathVariable("id") Long id,
            @RequestBody @Valid GuideDto newLocation,
            @RequestParam(required = false) @Digits(integer = 8, fraction = 5, message = "invalid price") Double price,
            Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serviceUseCase.updateService(id, newLocation, authentication.getName(), price));
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<ServiceDto> getService(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serviceUseCase.getService(id, authentication.getName()));
    }

    @PostMapping(value = "/get/price")
    public ResponseEntity<ServicePrice> getPrice(@RequestBody @Valid ServiceOrderDto serviceOrderDto) {
        serviceOrderDtoValidator.validate(serviceOrderDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ServicePrice(serviceUseCase.getServicePrice(serviceOrderDto)));
    }

    @PostMapping(value = "/get/route")
    public ResponseEntity<RouteDto> getRoute(@RequestBody @Valid PathDto pathDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serviceUseCase.getOptimalRoute(pathDto));
    }

    @GetMapping(value = "/track/{guideId}")
    public ResponseEntity<GuideDto> trackService(
            @PathVariable @ValidUUID String guideId,
            Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serviceUseCase.trackService(UUID.fromString(guideId), authentication.getName()));
    }
}
