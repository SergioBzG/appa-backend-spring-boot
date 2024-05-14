package com.sbz.appa.application.controller;


import com.sbz.appa.application.dto.GuideDto;
import com.sbz.appa.application.dto.PathDto;
import com.sbz.appa.application.dto.RouteDto;
import com.sbz.appa.application.dto.ServiceDto;
import com.sbz.appa.application.utils.ServicePrice;
import com.sbz.appa.core.usecase.ServiceUseCase;
import com.sbz.appa.infrastructure.persistence.repository.ServiceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/services")
@AllArgsConstructor
@Slf4j
public class ServiceController {

    private final ServiceUseCase serviceUseCase;
    private final ServiceRepository serviceRepository; // Used for testing

    @PostMapping(value = "/create")
    public ResponseEntity<ServiceDto> createService(@RequestBody ServiceDto serviceDto, Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(serviceUseCase.saveService(serviceDto, authentication.getName()));
    }

    /**
     * Only for testing
     */
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Void> deleteServices() {
        serviceRepository.deleteAll();
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PatchMapping(value = "/update")
    public ResponseEntity<ServiceDto> updateService(Long id, GuideDto newLocation) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serviceUseCase.updateService(id, newLocation));
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<ServiceDto> getService(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serviceUseCase.getService(id));
    }

    @GetMapping(value = "/price")
    public ResponseEntity<ServicePrice> getPrice(@RequestBody PathDto pathDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ServicePrice(serviceUseCase.getServicePrice(pathDto)));
    }

    @GetMapping(value = "/route")
    public ResponseEntity<RouteDto> getRoute(@RequestBody PathDto pathDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serviceUseCase.getOptimalRoute(pathDto));
    }

    @GetMapping(value = "/active")
    public ResponseEntity<ServiceDto> getRoute(Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serviceUseCase.getActiveService(authentication.getName()));
    }

    @GetMapping(value = "/track/{guideId}")
    public ResponseEntity<GuideDto> trackService(@PathVariable String guideId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serviceUseCase.trackService(UUID.fromString(guideId)));
    }
}
