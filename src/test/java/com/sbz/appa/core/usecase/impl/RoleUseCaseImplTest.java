package com.sbz.appa.core.usecase.impl;

import com.sbz.appa.application.dto.RoleDto;
import com.sbz.appa.application.exception.AlreadyExistsException;
import com.sbz.appa.core.mapper.Mapper;
import com.sbz.appa.infrastructure.persistence.entity.RoleEntity;
import com.sbz.appa.infrastructure.persistence.repository.RoleRepository;
import com.sbz.appa.util.RoleDtoTestData;
import com.sbz.appa.util.RoleEntityTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleUseCaseImplTest {

    @Mock
    private RoleRepository roleRepository;
    @Mock
    private Mapper<RoleEntity, RoleDto> roleMapper;
    @InjectMocks
    private RoleUseCaseImpl underTest;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Captor
    private ArgumentCaptor<RoleDto> roleDtoArgumentCaptor;

    @Test
    void testThatSaveRoleThrowsAlreadyException() {
        // Data for test
        RoleDto roleDto = RoleDtoTestData.createTestRoleDtoBison();
        RoleEntity roleEntity = RoleEntityTestData.createTestRoleEntityBison();
        when(roleRepository.findByName(stringArgumentCaptor.capture()))
                .thenReturn(Optional.of(roleEntity));

        // Invoke method (and assertion at the same time)
        AlreadyExistsException exception = assertThrows(
                AlreadyExistsException.class,
                () -> underTest.saveRole(roleDto)
        );

        // Assertions
        assertEquals("role with this name already exists", exception.getMessage());
        assertEquals(roleDto.getName(), stringArgumentCaptor.getValue());
        verifyNoInteractions(roleMapper);
    }

    @Test
    void testThatSaveReturnsRoleDto() {
        // Data for test
        RoleDto roleDto = RoleDtoTestData.createTestRoleDtoBison();
        RoleEntity roleEntity = RoleEntityTestData.createTestRoleEntityBison();
        when(roleRepository.findByName(stringArgumentCaptor.capture()))
                .thenReturn(Optional.empty());
        when(roleMapper.mapFromDto(roleDtoArgumentCaptor.capture()))
                .thenReturn(roleEntity);
        when(roleRepository.save(any(RoleEntity.class)))
                .thenReturn(roleEntity);
        when(roleMapper.mapToDto(any(RoleEntity.class)))
                .thenReturn(roleDto);

        // Invoke method
        RoleDto result = underTest.saveRole(roleDto);

        // Assertions
        assertEquals(roleDto, result);
        assertEquals(roleDto, roleDtoArgumentCaptor.getValue());
        verify(roleMapper, times(1)).mapToDto(roleEntity);
    }

    @Test
    void testThatGetRolesReturnsListOfRoleDto() {
        // Data for test
        List<RoleDto> roleDtos = List.of(
                RoleDtoTestData.createTestRoleDtoCitizen(),
                RoleDtoTestData.createTestRoleDtoBison()
        );
        List<RoleEntity> roleEntities = List.of(
                RoleEntityTestData.createTestRoleEntityCitizen(),
                RoleEntityTestData.createTestRoleEntityBison()
        );
        when(roleRepository.findAll())
                .thenReturn(roleEntities);
        when(roleMapper.mapToDto(any(RoleEntity.class)))
                .thenReturn(roleDtos.getFirst())
                .thenReturn(roleDtos.getLast());

        // Invoke method
        List<RoleDto> result = underTest.getRoles();

        // Assertions
        assertEquals(roleDtos.getFirst(), result.getFirst());
        assertEquals(roleDtos.getLast(), result.getLast());
        verify(roleMapper, times(2)).mapToDto(any(RoleEntity.class));
    }
}







































