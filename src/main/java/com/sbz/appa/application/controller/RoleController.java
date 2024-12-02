package com.sbz.appa.application.controller;


import com.sbz.appa.application.dto.RoleDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/roles")
public interface RoleController {

    @PostMapping(value = "/create")
    ResponseEntity<RoleDto> createRole(@RequestBody @Valid RoleDto role);

    @GetMapping(value = "/list")
    ResponseEntity<List<RoleDto>> listRoles();
}
