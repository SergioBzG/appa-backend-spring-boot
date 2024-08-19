package com.sbz.appa.util;

import com.sbz.appa.commons.Role;
import com.sbz.appa.infrastructure.persistence.entity.RoleEntity;

import java.util.List;

public class RoleEntityTestData {

    public static RoleEntity createTestRoleEntityCitizen() {
        return RoleEntity.builder()
                .name(Role.ROLE_CITIZEN.name())
                .description("this is a citizen")
                .users(List.of(
                        UserEntityTestData.createTestUserEntityCitizen(),
                        UserEntityTestData.createTestUserEntityCitizen1()
                ))
                .build();
    }

    public static RoleEntity createTestRoleEntityBison() {
        return RoleEntity.builder()
                .name(Role.ROLE_BISON.name())
                .description("this is a bison")
                .build();
    }
}
