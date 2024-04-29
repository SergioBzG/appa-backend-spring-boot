package com.sbz.appa.core.usecase;

import com.sbz.appa.application.dto.UserDto;

import java.util.List;

public interface UserUseCase {
    UserDto saveUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    void deleteUser(Long id);

    UserDto getUserById(Long id);

    UserDto getUserByEmail(String email);

    List<UserDto> getUsers();
}
