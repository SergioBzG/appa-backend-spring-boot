package com.sbz.appa.util;

import com.sbz.appa.application.dto.RoleDto;
import com.sbz.appa.commons.Role;


public class RoleDtoTestData {

    public static RoleDto createTestRoleDtoCitizen() {
        return RoleDto.builder()
                .name(Role.ROLE_CITIZEN.name())
                .description("this is a citizen")
                .build();
    }

    public static RoleDto createTestRoleDtoBison() {
        return RoleDto.builder()
                .name(Role.ROLE_BISON.name())
                .description("this is a bison")
                .build();
    }
}
