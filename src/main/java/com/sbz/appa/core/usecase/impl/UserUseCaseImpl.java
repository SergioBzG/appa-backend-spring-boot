package com.sbz.appa.core.usecase.impl;

import com.sbz.appa.application.dto.ServiceDto;
import com.sbz.appa.application.dto.UserDto;
import com.sbz.appa.application.exception.ActionNotAllowedException;
import com.sbz.appa.application.exception.AlreadyExistsException;
import com.sbz.appa.application.exception.NotFoundException;
import com.sbz.appa.commons.Role;
import com.sbz.appa.core.mapper.Mapper;
import com.sbz.appa.core.usecase.ServiceUseCase;
import com.sbz.appa.core.usecase.UserUseCase;
import com.sbz.appa.infrastructure.persistence.entity.RoleEntity;
import com.sbz.appa.infrastructure.persistence.entity.ServiceEntity;
import com.sbz.appa.infrastructure.persistence.entity.UserEntity;
import com.sbz.appa.infrastructure.persistence.repository.RoleRepository;
import com.sbz.appa.infrastructure.persistence.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Slf4j
public class UserUseCaseImpl implements UserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ServiceUseCase serviceUseCase;
    private final Mapper<UserEntity, UserDto> userMapper;
    private final Mapper<ServiceEntity, ServiceDto> serviceMapper;

    @Override
    public UserDto saveUser(UserDto userDto) {
        RoleEntity role = roleRepository.findByName(userDto.getRole())
                .orElseThrow(() -> new NotFoundException("role"));
        UserEntity userEntity = userMapper.mapFromDto(userDto);
        userEntity.setRole(role);
        UserEntity savedUser = userRepository.save(userEntity);

        // Look for order for the new bison
        if (savedUser.getRole().getName().equals(Role.ROLE_BISON.name()))
            serviceUseCase.searchForOrder(savedUser);

        return userMapper.mapToDto(savedUser);
    }

    @Transactional
    @Override
    public UserDto updateUser(UserDto userDto, String email) {
        UserEntity savedUser =  userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("user"));

        if (userDto.getPhone() != null
                && userRepository.findByPhone(userDto.getPhone())
                .filter(user -> !user.getEmail().equals(email)).isPresent())
            throw new AlreadyExistsException("user", "phone");
        else if (!userDto.getEmail().equals(email) && userRepository.findByEmail(userDto.getEmail()).isPresent())
            throw new AlreadyExistsException("user", "email");

        // Update User
        savedUser.setName(userDto.getName());
        savedUser.setEmail(userDto.getEmail());
        savedUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        savedUser.setPhone(userDto.getPhone());
        return userMapper.mapToDto(savedUser);
    }

    @Override
    public void deleteUser(Long id, String email) {
        UserEntity userToDelete = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("user"));
        UserEntity userRequester =  userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("user"));
        if (!userToDelete.equals(userRequester) && userRequester.getRole().getName().equals(Role.ROLE_ADMIN.name())) {
            if (!userToDelete.getRole().getName().equals(Role.ROLE_BISON.name()))
                throw new ActionNotAllowedException("user", "deleting");
            Optional<ServiceEntity> serviceToDeliver = userToDelete.getBisonOrders().stream()
                    .filter(service -> service.getArrived() == null)
                    .findFirst();
            serviceToDeliver.ifPresent(serviceUseCase::searchForBison);
        } else if (!userToDelete.equals(userRequester))
            throw new ActionNotAllowedException("user", "deleting");
        // Delete user
        userRepository.deleteById(id);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::mapToDto)
                .orElseThrow(() -> new NotFoundException("user"));
    }

    @Override
    public List<UserDto> getUserByRole(String role) {
        role = "ROLE_"+role.toUpperCase();
        RoleEntity roleEntity = roleRepository.findByName(role)
                .orElseThrow(() -> new NotFoundException("role"));
        return roleEntity.getUsers().stream()
                .map(userMapper::mapToDto)
                .toList();
    }

    @Override
    public List<ServiceDto> getUserServices(String email, String serviceType) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("user"));

        Stream<ServiceDto> userServices;
        if (userEntity.getRole().getName().equals(Role.ROLE_CITIZEN.name()))
            userServices = userEntity.getCitizenOrders().stream()
                    .map(serviceMapper::mapToDto);
        else
            userServices = userEntity.getBisonOrders().stream()
                    .map(serviceMapper::mapToDto);

        if (serviceType != null)
            userServices = userServices
                    .filter(service -> service.getType().equalsIgnoreCase(serviceType));
        return userServices.toList();
    }

    @Override
    public ServiceDto getLastService(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("user"));
        return userEntity.getCitizenOrders().stream()
                .max(Comparator.comparing(ServiceEntity::getCreated))
                .map(serviceMapper::mapToDto)
                .orElseThrow(() -> new NotFoundException("service"));
    }

    @Override
    public ServiceDto getActiveService(String email) {
        UserEntity bison = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("user"));
        return serviceUseCase.getActiveService(bison.getId())
                .orElseThrow(() -> new NotFoundException("service"));

    }
}