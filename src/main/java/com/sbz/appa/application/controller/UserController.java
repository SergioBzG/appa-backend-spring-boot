package com.sbz.appa.application.controller;

import com.sbz.appa.application.dto.ServiceDto;
import com.sbz.appa.application.dto.UserDto;
import com.sbz.appa.application.validator.Validator;
import com.sbz.appa.commons.Role;
import com.sbz.appa.core.usecase.UserUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserUseCase userUseCase;
    private final PasswordEncoder passwordEncoder;
    private final Validator<UserDto> staffValidator;

    @PostMapping(value = "/register/citizen")
    public ResponseEntity<UserDto> registerCitizen(@RequestBody @Valid UserDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_CITIZEN.name());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userUseCase.saveUser(user));
    }

    @PostMapping(value = "/register/staff")
    public ResponseEntity<UserDto> registerStaff(@RequestBody @Valid UserDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        staffValidator.validate(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userUseCase.saveUser(user));
    }

    @GetMapping(value = "/login")
    public ResponseEntity<UserDto> getUser(Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userUseCase.getUserByEmail(authentication.getName()));
    }

    @PatchMapping(value = "/update")
    public ResponseEntity<UserDto> updatedUser(
            @RequestBody @Valid UserDto user,
            Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userUseCase.updateUser(user, authentication.getName()));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id, Authentication authentication) {
        userUseCase.deleteUser(id, authentication.getName());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping(value = "/role/{role}")
    public ResponseEntity<List<UserDto>> getUserByRole(@PathVariable("role") String role) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userUseCase.getUserByRole(role));
    }

    @GetMapping(value = "/get/services")
    public ResponseEntity<List<ServiceDto>> getUserServices(
            @RequestParam(value = "type", required = false) String serviceType,
            Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userUseCase.getUserServices(authentication.getName(), serviceType));
    }

    @GetMapping(value = "/get/services/last-service")
    public ResponseEntity<ServiceDto> getLastServiceOfUser(Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userUseCase.getLastService(authentication.getName()));
    }

    @GetMapping(value = "/get/services/active")
    public ResponseEntity<ServiceDto> getActiveServiceOfBison(Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userUseCase.getActiveService(authentication.getName()));
    }

}
