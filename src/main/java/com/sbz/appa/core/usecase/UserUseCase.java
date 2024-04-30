package com.sbz.appa.core.usecase;

import com.sbz.appa.application.dto.UserDto;

import java.util.List;

public interface UserUseCase {
    UserDto saveUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, String email);

    void deleteUser(Long id, String email);

    UserDto getUserById(Long id);

    UserDto getUserByEmail(String email);

    List<UserDto> getUsers();
}
