package com.sbz.appa.core.usecase;

import com.sbz.appa.application.dto.RoleDto;

import java.util.List;

public interface RoleUseCase {
    RoleDto saveRole(RoleDto roleDto);

    List<RoleDto> getRoles();

}
