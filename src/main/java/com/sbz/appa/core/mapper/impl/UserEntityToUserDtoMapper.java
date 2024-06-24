package com.sbz.appa.core.mapper.impl;

import com.sbz.appa.application.dto.UserDto;
import com.sbz.appa.core.mapper.Mapper;
import com.sbz.appa.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToUserDtoMapper implements Mapper<UserEntity, UserDto> {
    @Override
    public UserDto mapToDto(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .role(userEntity.getRole().getName())
                .document(userEntity.getDocument())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .vehicle(userEntity.getVehicle())
                .available(userEntity.getAvailable())
                .lastDelivery(userEntity.getLastDelivery())
                .build();
    }

    @Override
    public UserEntity mapFromDto(UserDto userDto) {
        return UserEntity.builder()
                .id(userDto.getId())
                .document(userDto.getDocument())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .phone(userDto.getPhone())
                .vehicle(userDto.getVehicle())
                .available(userDto.getAvailable())
                .lastDelivery(userDto.getLastDelivery())
                .build();
    }
}
