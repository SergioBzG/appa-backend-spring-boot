package com.sbz.appa.application.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/users")
@AllArgsConstructor
public class UserController {

    @PostMapping(value = "/register/citizen")
    public ResponseEntity<User> registerCitizen(@RequestBody User user) {
        return null;
    }

    @GetMapping
    public ResponseEntity<String> getUser() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Keep it going");
    }
}
