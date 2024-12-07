package com.sbz.appa.application.controller;

import com.sbz.appa.application.dto.ServiceDto;
import com.sbz.appa.application.dto.GuideDto;
import com.sbz.appa.application.dto.ServiceOrderDto;
import com.sbz.appa.application.dto.PathDto;
import com.sbz.appa.application.dto.RouteDto;
import com.sbz.appa.application.util.ServicePrice;
import com.sbz.appa.application.validator.annotation.ValidUUID;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RequestMapping(path = "/v1/services")
@Validated
public interface ServiceController {

    @PostMapping(value = "/create")
    ResponseEntity<ServiceDto> createService(@RequestBody @Valid ServiceDto serviceDto, Authentication authentication);

    @PatchMapping(value = "/update/{id}")
    ResponseEntity<ServiceDto> updateService(
            @PathVariable("id") Long id,
            @RequestBody @Valid GuideDto newLocation,
            @RequestParam(required = false) @Digits(integer = 8, fraction = 5, message = "invalid price") Double price,
            Authentication authentication);

    @GetMapping(value = "/get/{id}")
    ResponseEntity<ServiceDto> getService(@PathVariable Long id, Authentication authentication);

    @PostMapping(value = "/get/price")
    ResponseEntity<ServicePrice> getPrice(@RequestBody @Valid ServiceOrderDto serviceOrderDto);

    @PostMapping(value = "/get/route")
    ResponseEntity<RouteDto> getRoute(@RequestBody @Valid PathDto pathDto);

    @GetMapping(value = "/track/{guideId}")
    ResponseEntity<GuideDto> trackService(@PathVariable @ValidUUID String guideId, Authentication authentication);
}
