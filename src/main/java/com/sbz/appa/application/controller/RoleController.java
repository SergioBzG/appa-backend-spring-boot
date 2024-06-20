package com.sbz.appa.application.controller;


import com.sbz.appa.application.dto.RoleDto;
import com.sbz.appa.core.usecase.RoleUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/roles")
@AllArgsConstructor
public class RoleController {

    private final RoleUseCase roleUseCase;

    @PostMapping(value = "/create")
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto role) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(roleUseCase.saveRole(role));
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<RoleDto>> listRoles() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(roleUseCase.getRoles());
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable("id") Long id) {
        roleUseCase.deleteRole(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
