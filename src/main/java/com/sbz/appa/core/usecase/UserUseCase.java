package com.sbz.appa.core.usecase;

import com.sbz.appa.application.dto.UserDto;

import java.util.List;

public interface UserUseCase {
    UserDto saveUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, String email);

    void deleteUser(Long id, String email);

    UserDto getUserByEmail(String email);

    List<UserDto> getUserByRole(String role);
}
