package com.sbz.appa.util;

import com.sbz.appa.application.dto.UserDto;
import com.sbz.appa.commons.Role;

public class UserDtoTestData {

    public static UserDto createTestUserDtoCitizen() {
        return UserDto.builder()
                .role(Role.ROLE_CITIZEN.name())
                .name("Kenny")
                .email("kenny@appa.com")
                .password("kenny123")
                .phone("1234567")
                .available(true)
                .build();
    }

    public static UserDto createTestUserDtoCitizen1() {
        return UserDto.builder()
                .role(Role.ROLE_CITIZEN.name())
                .name("Kyle")
                .email("kyle@appa.com")
                .password("kyle123")
                .phone("45476824")
                .available(true)
                .build();
    }

    public static UserDto createTestUserDtoBison() {
        return UserDto.builder()
                .role(Role.ROLE_BISON.name())
                .name("Eric")
                .document("CC-234667")
                .email("eric@appa.com")
                .password("eric123")
                .phone("785343")
                .vehicle("ERT-842")
                .available(true)
                .build();
    }

    public static UserDto createTestUserDtoToUpdating() {
        return UserDto.builder()
                .name("newName")
                .email("newEmail@appa.com")
                .password("newPassword")
                .phone("842332")
                .available(true)
                .build();
    }

    public static UserDto createTestUserDtoUpdated() {
        return UserDto.builder()
                .role(Role.ROLE_CITIZEN.name())
                .name("newName")
                .email("newEmail@appa.com")
                .password("newPassword")
                .phone("842332")
                .available(true)
                .build();
    }
}
