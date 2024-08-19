package com.sbz.appa.util;


import com.sbz.appa.commons.Role;
import com.sbz.appa.infrastructure.persistence.entity.RoleEntity;
import com.sbz.appa.infrastructure.persistence.entity.UserEntity;

import java.util.List;

public class UserEntityTestData {

    public static UserEntity createTestUserEntityCitizen() {
        return UserEntity.builder()
                .name("Kenny")
                .role(RoleEntity.builder()
                        .name(Role.ROLE_CITIZEN.name())
                        .build()
                )
                .email("kenny@appa.com")
                .password("kenny123")
                .phone("1234567")
                .available(true)
                .citizenOrders(List.of(
                        ServiceEntityTestData.createTestServiceEntityCarriage(),
                        ServiceEntityTestData.createTestServiceEntityPackage()
                ))
                .build();
    }

    public static UserEntity createTestUserEntityCitizen1() {
        return UserEntity.builder()
                .name("Kyle")
                .role(RoleEntity.builder()
                        .name(Role.ROLE_CITIZEN.name())
                        .build()
                )
                .email("kyle@appa.com")
                .password("kyle123")
                .phone("45476824")
                .available(true)
                .build();
    }

    public static UserEntity createTestUserEntityCitizenWithOutServices() {
        return UserEntity.builder()
                .name("Kyle")
                .role(RoleEntity.builder()
                        .name(Role.ROLE_CITIZEN.name())
                        .build()
                )
                .email("kyle@appa.com")
                .password("kyle123")
                .phone("45476824")
                .available(true)
                .citizenOrders(List.of())
                .build();
    }

    public static UserEntity createTestUserEntityCitizenWithTheSamePhone() {
        return UserEntity.builder()
                .role(RoleEntity.builder()
                        .name(Role.ROLE_CITIZEN.name())
                        .build()
                )
                .name("Kate")
                .email("kate@appa.com")
                .password("kate123")
                .phone("842332")
                .available(true)
                .build();
    }

    public static UserEntity createTestUserEntityCitizenWithTheSameEmail() {
        return UserEntity.builder()
                .name("Kate")
                .role(RoleEntity.builder()
                        .name(Role.ROLE_CITIZEN.name())
                        .build()
                )
                .email("newEmail@appa.com")
                .password("kate123")
                .phone("842332")
                .available(true)
                .build();
    }

    public static UserEntity createTestUserEntityBison() {
        return UserEntity.builder()
                .name("Eric")
                .role(RoleEntity.builder()
                        .name(Role.ROLE_BISON.name())
                        .build()
                )
                .document("CC-234667")
                .email("eric@appa.com")
                .password("eric123")
                .phone("785343")
                .vehicle("ERT-842")
                .available(true)
                .bisonOrders(List.of(
                        ServiceEntityTestData.createTestServiceEntityCarriage(),
                        ServiceEntityTestData.createTestServiceEntityPackage()
                ))
                .build();
    }

    public static UserEntity createTestUserEntityAdmin() {
        return UserEntity.builder()
                .name("Mari")
                .role(RoleEntity.builder()
                        .name(Role.ROLE_ADMIN.name())
                        .build()
                )
                .email("mari@appa.com")
                .password("eric123")
                .available(true)
                .build();
    }
}
