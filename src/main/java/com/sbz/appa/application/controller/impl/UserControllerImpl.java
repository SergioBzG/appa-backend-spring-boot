package com.sbz.appa.application.controller.impl;

import com.sbz.appa.application.controller.UserController;
import com.sbz.appa.application.dto.ServiceDto;
import com.sbz.appa.application.dto.UserDto;
import com.sbz.appa.application.validator.Validator;
import com.sbz.appa.commons.Role;
import com.sbz.appa.core.usecase.UserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@AllArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserUseCase userUseCase;
    private final PasswordEncoder passwordEncoder;
    private final Validator<UserDto> staffValidator;

    public ResponseEntity<UserDto> registerCitizen(UserDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_CITIZEN.name());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userUseCase.saveUser(user));
    }

    public ResponseEntity<UserDto> registerStaff(UserDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        staffValidator.validate(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userUseCase.saveUser(user));
    }

    public ResponseEntity<UserDto> getUser(Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userUseCase.getUserByEmail(authentication.getName()));
    }

    public ResponseEntity<UserDto> updatedUser(UserDto user, Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userUseCase.updateUser(user, authentication.getName()));
    }

    public ResponseEntity<Void> deleteUser(Long id, Authentication authentication) {
        userUseCase.deleteUser(id, authentication.getName());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    public ResponseEntity<List<UserDto>> getUserByRole(String role) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userUseCase.getUserByRole(role));
    }

    public ResponseEntity<List<ServiceDto>> getUserServices(String serviceType, Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userUseCase.getUserServices(authentication.getName(), serviceType));
    }

    public ResponseEntity<ServiceDto> getLastServiceOfUser(Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userUseCase.getLastService(authentication.getName()));
    }

    public ResponseEntity<ServiceDto> getActiveServiceOfBison(Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userUseCase.getActiveService(authentication.getName()));
    }
}
