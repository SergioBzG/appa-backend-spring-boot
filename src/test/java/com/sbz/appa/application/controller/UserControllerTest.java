package com.sbz.appa.application.controller;

import com.sbz.appa.application.dto.ServiceDto;
import com.sbz.appa.application.dto.UserDto;
import com.sbz.appa.application.validator.Validator;
import com.sbz.appa.core.usecase.UserUseCase;
import com.sbz.appa.util.ServiceDtoTestData;
import com.sbz.appa.util.UserDtoTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserUseCase userUseCase;
    @Mock
    private PasswordEncoder bCryptPasswordEncoder;
    @Mock
    private Validator<UserDto> staffValidator;
    @InjectMocks
    private UserController underTest;

    @Captor
    private ArgumentCaptor<UserDto> userDtoArgumentCaptor;
    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;
    @Captor
    private ArgumentCaptor<Long> longArgumentCaptor;

    @Test
    void testThatRegisterCitizenSuccessfullyReturnsUserDto() {
        // Data for test
        UserDto userToCreate = UserDtoTestData.createTestUserDtoCitizen();
        String initialPassword = userToCreate.getPassword();
        when(bCryptPasswordEncoder.encode(stringArgumentCaptor.capture()))
                .thenReturn("encodedPassword");
        when(userUseCase.saveUser(any(UserDto.class)))
                .thenReturn(userToCreate);

        // Invoke method
        ResponseEntity<UserDto> response = underTest.registerCitizen(userToCreate);

        // Assertions
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userToCreate, response.getBody());
        assertEquals(initialPassword, stringArgumentCaptor.getValue());
        verify(userUseCase, times(1)).saveUser(userToCreate);
    }

    @Test
    void testThatRegisterStaffSuccessfullyReturnsUserDto() {
        // Data for test
        UserDto userToCreate = UserDtoTestData.createTestUserDtoBison();
        String initialPassword = userToCreate.getPassword();
        when(bCryptPasswordEncoder.encode(stringArgumentCaptor.capture()))
                .thenReturn("encodedPassword");
        when(userUseCase.saveUser(any(UserDto.class)))
                .thenReturn(userToCreate);

        // Invoke method
        ResponseEntity<UserDto> response = underTest.registerStaff(userToCreate);

        // Assertions
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userToCreate, response.getBody());
        assertEquals(initialPassword, stringArgumentCaptor.getValue());
        verify(userUseCase, times(1)).saveUser(userToCreate);
        verify(staffValidator, times(1)).validate(userToCreate);
    }



    @Test
    void testThatGetUserSuccessfullyReturnsUserDto() {
        // Data for test
        UserDto userToGet = UserDtoTestData.createTestUserDtoCitizen();
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(userToGet.getEmail());
        when(userUseCase.getUserByEmail(stringArgumentCaptor.capture()))
                .thenReturn(userToGet);

        // Invoke method
        ResponseEntity<UserDto> response = underTest.getUser(authentication);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userToGet, response.getBody());
        assertEquals(userToGet.getEmail(), stringArgumentCaptor.getValue());
        verify(userUseCase, times(1)).getUserByEmail(anyString());
    }

    @Test
    void testThatUpdatedUserSuccessfullyReturnsUserDto() {
        // Data for test
        UserDto userToUpdate = UserDtoTestData.createTestUserDtoCitizen();
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(userToUpdate.getEmail());
        when(userUseCase.updateUser(userDtoArgumentCaptor.capture(), anyString()))
                .thenReturn(userToUpdate);

        // Invoke method
        ResponseEntity<UserDto> response = underTest.updatedUser(userToUpdate, authentication);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userToUpdate, response.getBody());
        assertEquals(userToUpdate, userDtoArgumentCaptor.getValue());
        verify(userUseCase, times(1)).updateUser(userToUpdate, userToUpdate.getEmail());
    }

    @Test
    void testThatDeleteUserSuccessfullyReturnsNoContent() {
        // Data for test
        Long userId = 1L;
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("anyEmail");
        doNothing().when(userUseCase).deleteUser(longArgumentCaptor.capture(), stringArgumentCaptor.capture());

        // Invoke method
        ResponseEntity<Void> response = underTest.deleteUser(userId, authentication);

        // Assertions
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("anyEmail", stringArgumentCaptor.getValue());
        assertEquals(userId, longArgumentCaptor.getValue());
        verify(userUseCase, times(1)).deleteUser(userId, authentication.getName());
    }

    @Test
    void testThatGetUserByRoleSuccessfullyReturnsListOfUserDto() {
        // Data for test
        List<UserDto> usersDtoByRole = List.of(
            UserDtoTestData.createTestUserDtoCitizen(),
            UserDtoTestData.createTestUserDtoCitizen1()
        );
        String roleSearched = "CITIZEN";
        when(userUseCase.getUserByRole(stringArgumentCaptor.capture()))
                .thenReturn(usersDtoByRole);

        // Invoke method
        ResponseEntity<List<UserDto>> response = underTest.getUserByRole(roleSearched);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usersDtoByRole, response.getBody());
        assertEquals(roleSearched, stringArgumentCaptor.getValue());
        verify(userUseCase, times(1)).getUserByRole(anyString());
    }

    @Test
    void testThatGetUserServicesSuccessfullyReturnsListOfServiceDto() {
        // Data for test
        List<ServiceDto> servicesDto = List.of(
                ServiceDtoTestData.createTestServiceDtoCarriage(),
                ServiceDtoTestData.createTestServiceDtoPackage()
        );
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("anyEmail");
        when(userUseCase.getUserServices(stringArgumentCaptor.capture(), anyString()))
                .thenReturn(servicesDto);

        // Invoke Method
        ResponseEntity<List<ServiceDto>> response = underTest.getUserServices("type", authentication);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(servicesDto, response.getBody());
        assertEquals(authentication.getName(), stringArgumentCaptor.getValue());
        verify(userUseCase, times(1)).getUserServices(anyString(), anyString());
    }

    @Test
    void testThatGetLastServiceOfUserSuccessfullyReturnsServiceDto() {
        // Data for test
        ServiceDto serviceToGet = ServiceDtoTestData.createTestServiceDtoCarriage();
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("anyEmail");
        when(userUseCase.getLastService(stringArgumentCaptor.capture()))
                .thenReturn(serviceToGet);

        // Invoke Method
        ResponseEntity<ServiceDto> response = underTest.getLastServiceOfUser(authentication);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(serviceToGet, response.getBody());
        assertEquals(authentication.getName(), stringArgumentCaptor.getValue());
        verify(userUseCase, times(1)).getLastService(anyString());
    }

    @Test
    void testThatGetActiveServiceOfBisonSuccessfullyReturnsServiceDto() {
        // Data for test
        ServiceDto serviceToGet = ServiceDtoTestData.createTestServiceDtoPackage();
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("anyEmail");
        when(userUseCase.getActiveService(stringArgumentCaptor.capture()))
                .thenReturn(serviceToGet);

        // Invoke Method
        ResponseEntity<ServiceDto> response = underTest.getActiveServiceOfBison(authentication);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(serviceToGet, response.getBody());
        assertEquals(authentication.getName(), stringArgumentCaptor.getValue());
        verify(userUseCase, times(1)).getActiveService(anyString());
    }
}