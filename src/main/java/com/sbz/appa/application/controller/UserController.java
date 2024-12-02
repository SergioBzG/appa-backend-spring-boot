package com.sbz.appa.application.controller;


import com.sbz.appa.application.dto.ServiceDto;
import com.sbz.appa.application.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/users")
public interface UserController {

    @PostMapping(value = "/register/citizen")
    ResponseEntity<UserDto> registerCitizen(@RequestBody @Valid UserDto user);

    @PostMapping(value = "/register/staff")
    ResponseEntity<UserDto> registerStaff(@RequestBody @Valid UserDto user);

    @GetMapping(value = "/login")
    ResponseEntity<UserDto> getUser(Authentication authentication);

    @PatchMapping(value = "/update")
    ResponseEntity<UserDto> updatedUser(@RequestBody @Valid UserDto user, Authentication authentication);

    @DeleteMapping(value = "/delete/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable("id") Long id, Authentication authentication);

    @GetMapping(value = "/role/{role}")
    ResponseEntity<List<UserDto>> getUserByRole(@PathVariable("role") String role);

    @GetMapping(value = "/get/services")
    ResponseEntity<List<ServiceDto>> getUserServices(
            @RequestParam(value = "type", required = false) String serviceType,
            Authentication authentication);

    @GetMapping(value = "/get/services/last-service")
    ResponseEntity<ServiceDto> getLastServiceOfUser(Authentication authentication);

    @GetMapping(value = "/get/services/active")
    ResponseEntity<ServiceDto> getActiveServiceOfBison(Authentication authentication);
}
