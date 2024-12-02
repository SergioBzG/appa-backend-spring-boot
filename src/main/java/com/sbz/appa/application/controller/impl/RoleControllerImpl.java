package com.sbz.appa.application.controller.impl;


import com.sbz.appa.application.controller.RoleController;
import com.sbz.appa.application.dto.RoleDto;
import com.sbz.appa.core.usecase.RoleUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@AllArgsConstructor
public class RoleControllerImpl implements RoleController {

    private final RoleUseCase roleUseCase;

    public ResponseEntity<RoleDto> createRole(RoleDto role) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(roleUseCase.saveRole(role));
    }

    public ResponseEntity<List<RoleDto>> listRoles() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(roleUseCase.getRoles());
    }
}
