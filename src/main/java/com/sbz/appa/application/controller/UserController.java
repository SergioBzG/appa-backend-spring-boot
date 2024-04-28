package com.sbz.appa.application.controller;

import com.sbz.appa.infrastructure.persistence.entity.UserEntity;
import com.sbz.appa.infrastructure.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @PostMapping(value = "/register/citizen")
    public ResponseEntity<User> registerCitizen(@RequestBody User user) {
        return null;
    }

    @GetMapping
    public ResponseEntity<UserEntity> getUser(Authentication authentication) {
        UserEntity user = userRepository.findByEmail(authentication.getName()).orElse(null);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }
}
