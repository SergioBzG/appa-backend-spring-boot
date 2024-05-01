package com.sbz.appa.core.usecase.impl;

import com.sbz.appa.application.dto.UserDto;
import com.sbz.appa.core.mapper.Mapper;
import com.sbz.appa.core.usecase.UserUseCase;
import com.sbz.appa.infrastructure.persistence.entity.RoleEntity;
import com.sbz.appa.infrastructure.persistence.entity.UserEntity;
import com.sbz.appa.infrastructure.persistence.repository.RoleRepository;
import com.sbz.appa.infrastructure.persistence.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserUseCaseImpl implements UserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final Mapper<UserEntity, UserDto> userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto saveUser(UserDto userDto) {
        RoleEntity role = roleRepository.findByName(userDto.getRole())
                .orElseThrow(() -> new IllegalStateException("Role not found"));
        UserEntity userEntity = userMapper.mapFrom(userDto);
        userEntity.setRole(role);
        return userMapper.mapTo(userRepository.save(userEntity));
    }

    @Transactional
    @Override
    public UserDto updateUser(UserDto userDto, String email) {
        UserEntity savedUser =  userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        if (userDto.getPhone() != null && userRepository.findByPhone(userDto.getPhone()).filter(user -> !user.getEmail().equals(email)).isPresent())
            throw new IllegalStateException("A user already exists with this phone");
        else if (!userDto.getEmail().equals(email) && userRepository.findByEmail(userDto.getEmail()).isPresent())
            throw new IllegalStateException("A user already exists with this email");

        // Update User
        savedUser.setName(userDto.getName());
        savedUser.setEmail(userDto.getEmail());
        savedUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        savedUser.setPhone(userDto.getPhone());
        return userMapper.mapTo(savedUser);
    }

    @Override
    public void deleteUser(Long id, String email) {
        UserEntity userToDelete = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("User with this id not found"));
        UserEntity userRequester =  userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        if (!userToDelete.equals(userRequester) && userRequester.getRole().getName().equals("ROLE_ADMIN")) {
            // TODO : type logic to looking for a new bison who takes the service that deleted bison had assigned, in case of deleted bison has an assigned service
        } else if (!userToDelete.equals(userRequester))
            throw new IllegalStateException("Incorrect user id");
        // Delete user
        userRepository.deleteById(id);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::mapTo)
                .orElseThrow(() -> new IllegalStateException("User not found"));
    }

    @Override
    public List<UserDto> getUserByRole(String role) {
        role = "ROLE_"+role.toUpperCase();
        RoleEntity roleEntity = roleRepository.findByName(role)
                .orElseThrow(() -> new IllegalStateException("Role not found"));
        return roleEntity.getUsers().stream()
                .map(userMapper::mapTo)
                .toList();
    }
}
