package com.sbz.appa.core.mapper.impl;

import com.sbz.appa.application.dto.RoleDto;
import com.sbz.appa.core.mapper.Mapper;
import com.sbz.appa.infrastructure.persistence.entity.RoleEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Component
public class RoleEntityToRoleDto implements Mapper<RoleEntity, RoleDto> {
    private final ModelMapper modelMapper;

    @Override
    public RoleDto mapTo(RoleEntity roleEntity) {
        return modelMapper.map(roleEntity, RoleDto.class);
    }

    @Override
    public RoleEntity mapFrom(RoleDto roleDto) {
        return modelMapper.map(roleDto, RoleEntity.class);
    }
}
