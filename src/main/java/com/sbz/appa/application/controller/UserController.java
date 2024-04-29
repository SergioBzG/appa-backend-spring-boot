package com.sbz.appa.application.controller;

import com.sbz.appa.application.dto.UserDto;
import com.sbz.appa.core.usecase.UserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserUseCase userUseCase;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(value = "/register/citizen")
    public ResponseEntity<UserDto> registerCitizen(@RequestBody UserDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_CITIZEN");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userUseCase.saveUser(user));
    }

    @PostMapping(value = "/register/staff")
    public ResponseEntity<UserDto> registerStaff(@RequestBody UserDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Validate user role
        if(!(user.getRole().equals("ROLE_BISON") || user.getRole().equals("ROLE_ADMIN"))) {
            throw new IllegalArgumentException("Invalid role");
        }
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
}
