package com.sbz.appa.core.usecase.impl;

import com.sbz.appa.application.dto.UserDto;
import com.sbz.appa.core.mapper.Mapper;
import com.sbz.appa.core.usecase.UserUseCase;
import com.sbz.appa.infrastructure.persistence.entity.RoleEntity;
import com.sbz.appa.infrastructure.persistence.entity.UserEntity;
import com.sbz.appa.infrastructure.persistence.repository.RoleRepository;
import com.sbz.appa.infrastructure.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserUseCaseImpl implements UserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final Mapper<UserEntity, UserDto> userMapper;

    @Override
    public UserDto saveUser(UserDto userDto) {
        RoleEntity role = roleRepository.findByName(userDto.getRole())
                .orElseThrow(() -> new IllegalStateException("Role not found"));
        UserEntity userEntity = userMapper.mapFrom(userDto);
        userEntity.setRole(role);
        return userMapper.mapTo(userRepository.save(userEntity));
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public UserDto getUserById(Long id) {
        return null;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::mapTo)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<UserDto> getUsers() {
        return List.of();
    }
}
