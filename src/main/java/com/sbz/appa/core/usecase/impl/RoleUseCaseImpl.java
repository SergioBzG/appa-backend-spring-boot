package com.sbz.appa.core.usecase.impl;

import com.sbz.appa.application.dto.RoleDto;
import com.sbz.appa.application.exception.AlreadyExistsException;
import com.sbz.appa.core.mapper.Mapper;
import com.sbz.appa.core.usecase.RoleUseCase;
import com.sbz.appa.infrastructure.persistence.entity.RoleEntity;
import com.sbz.appa.infrastructure.persistence.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleUseCaseImpl implements RoleUseCase {

    private final RoleRepository roleRepository;
    private final Mapper<RoleEntity, RoleDto> roleMapper;

    @Override
    public RoleDto saveRole(RoleDto roleDto) {
        roleRepository.findByName(roleDto.getName())
                .ifPresent(role -> {
                    throw new AlreadyExistsException("role", "name");
                });
        return roleMapper.mapToDto(
                roleRepository.save(roleMapper.mapFromDto(roleDto))
        );
    }

    @Override
    public List<RoleDto> getRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::mapToDto)
                .toList();
    }
}
