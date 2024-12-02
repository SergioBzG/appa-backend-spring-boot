package com.sbz.appa.application.controller.impl;

import com.sbz.appa.application.dto.RoleDto;
import com.sbz.appa.core.usecase.RoleUseCase;
import com.sbz.appa.util.RoleDtoTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleControllerTest {

    @Mock
    private RoleUseCase roleUseCase;
    @InjectMocks
    private RoleControllerImpl underTest;

    @Captor
    private ArgumentCaptor<RoleDto> roleDtoArgumentCaptor;

    @Test
    void testThatCreateRoleSuccessfullyReturnsRoleDto() {
        // Data for test
        RoleDto roleDto = RoleDtoTestData.createTestRoleDtoCitizen();
        when(roleUseCase.saveRole(roleDtoArgumentCaptor.capture()))
                .thenReturn(roleDto);

        // Invoke method
        ResponseEntity<RoleDto> response = underTest.createRole(roleDto);

        // Assertions
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(roleDto, response.getBody());
        assertEquals(roleDto, roleDtoArgumentCaptor.getValue());
    }

    @Test
    void testThatListRolesReturnsListOfRoleDto() {
        // Data for test
        RoleDto roleDtoCitizen = RoleDtoTestData.createTestRoleDtoCitizen();
        RoleDto roleDtoBison = RoleDtoTestData.createTestRoleDtoBison();
        List<RoleDto> roles = List.of(roleDtoCitizen, roleDtoBison);
        when(roleUseCase.getRoles())
                .thenReturn(roles);

        // Invoke method
        ResponseEntity<List<RoleDto>> response = underTest.listRoles();

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(roles, response.getBody());
        verify(roleUseCase, times(1)).getRoles();
    }
}