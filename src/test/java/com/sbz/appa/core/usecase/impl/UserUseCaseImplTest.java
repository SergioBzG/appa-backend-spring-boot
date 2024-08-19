package com.sbz.appa.core.usecase.impl;

import com.sbz.appa.application.dto.ServiceDto;
import com.sbz.appa.application.dto.UserDto;
import com.sbz.appa.application.exception.ActionNotAllowedException;
import com.sbz.appa.application.exception.AlreadyExistsException;
import com.sbz.appa.application.exception.NotFoundException;
import com.sbz.appa.core.mapper.Mapper;
import com.sbz.appa.core.usecase.ServiceUseCase;
import com.sbz.appa.infrastructure.persistence.entity.RoleEntity;
import com.sbz.appa.infrastructure.persistence.entity.ServiceEntity;
import com.sbz.appa.infrastructure.persistence.entity.UserEntity;
import com.sbz.appa.infrastructure.persistence.repository.RoleRepository;
import com.sbz.appa.infrastructure.persistence.repository.UserRepository;
import com.sbz.appa.util.RoleEntityTestData;
import com.sbz.appa.util.ServiceDtoTestData;
import com.sbz.appa.util.UserDtoTestData;
import com.sbz.appa.util.UserEntityTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ServiceUseCase serviceUseCase;
    @Mock
    private Mapper<UserEntity, UserDto> userMapper;
    @Mock
    private Mapper<ServiceEntity, ServiceDto> serviceMapper;

    private UserUseCaseImpl underTest;

    @Captor
    private ArgumentCaptor<UserDto> userDtoArgumentCaptor;
    @Captor
    private ArgumentCaptor<UserEntity> userEntityArgumentCaptor;
    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;
    @Captor
    private ArgumentCaptor<Long> longArgumentCaptor;
    @Captor
    private ArgumentCaptor<ServiceEntity> serviceEntityArgumentCaptor;

    @BeforeEach
    void setUp() {
        underTest = new UserUseCaseImpl(
                userRepository,
                roleRepository,
                passwordEncoder,
                serviceUseCase,
                userMapper,
                serviceMapper
        );
    }

    @Test
    void testThatSaveUserSuccessfullyReturnsUserDto() {
        // Data for test
        UserDto userToSave = UserDtoTestData.createTestUserDtoCitizen();
        UserEntity userSaved = UserEntityTestData.createTestUserEntityCitizen();
        RoleEntity roleEntity = RoleEntityTestData.createTestRoleEntityCitizen();
        when(roleRepository.findByName(anyString()))
                .thenReturn(Optional.of(roleEntity));
        when(userMapper.mapFromDto(userDtoArgumentCaptor.capture()))
                .thenReturn(userSaved);
        when(userRepository.save(userEntityArgumentCaptor.capture()))
                .thenReturn(userSaved);
        when(userMapper.mapToDto(any(UserEntity.class)))
                .thenReturn(userToSave);

        // Invoke method
        UserDto result = underTest.saveUser(userToSave);

        // Assertions
        assertEquals(userToSave, result);
        assertEquals(userToSave, userDtoArgumentCaptor.getValue());
        assertEquals(userSaved, userEntityArgumentCaptor.getValue());
        assertEquals(roleEntity.getName(), result.getRole());
        verify(roleRepository, times(1)).findByName(anyString());
    }

    @Test
    void testThatSaveUserThrowsNotFoundExceptionWhenRoleNotFound() {
        // Data for test
        UserDto userToSave = UserDtoTestData.createTestUserDtoCitizen();
        when(roleRepository.findByName(stringArgumentCaptor.capture()))
                .thenReturn(Optional.empty());

        // Invoke method (and assertion at the same time)
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> underTest.saveUser(userToSave)
        );

        // Assertions
        assertEquals("role not found", exception.getMessage());
        assertEquals(userToSave.getRole(), stringArgumentCaptor.getValue());
        verify(roleRepository, times(1)).findByName(anyString());
    }
    
    @Test
    void testThatWhenUserToSaveIsBisonThenSearchForOrderMethodIsInvoked() {
        // Data for test
        UserDto userToSave = UserDtoTestData.createTestUserDtoBison();
        UserEntity userSaved = UserEntityTestData.createTestUserEntityBison();
        RoleEntity roleEntity = RoleEntityTestData.createTestRoleEntityBison();
        when(roleRepository.findByName(anyString()))
                .thenReturn(Optional.of(roleEntity));
        when(userMapper.mapFromDto(userDtoArgumentCaptor.capture()))
                .thenReturn(userSaved);
        when(userRepository.save(userEntityArgumentCaptor.capture()))
                .thenReturn(userSaved);
        when(userMapper.mapToDto(any(UserEntity.class)))
                .thenReturn(userToSave);

        // Invoke method
        UserDto result = underTest.saveUser(userToSave);

        // Assertions
        assertEquals(userToSave, result);
        assertEquals(userToSave, userDtoArgumentCaptor.getValue());
        assertEquals(userSaved, userEntityArgumentCaptor.getValue());
        assertEquals(roleEntity.getName(), result.getRole());
        verify(serviceUseCase, times(1)).searchForOrder(userSaved);
    }

    @Test
    void testThatUpdateUserSuccessfullyReturnsUpdatedUserDto() {
        // Test for data
        UserDto userDtoWithNewData = UserDtoTestData.createTestUserDtoToUpdating();
        UserEntity userSaved = UserEntityTestData.createTestUserEntityCitizen();
        UserDto userUpdated = UserDtoTestData.createTestUserDtoUpdated();
        String emailOfRequester = userSaved.getEmail();
        when(userRepository.findByEmail(stringArgumentCaptor.capture()))
                .thenReturn(Optional.of(userSaved))
                .thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString()))
                .thenReturn(userDtoWithNewData.getPassword());
        when(userMapper.mapToDto(any(UserEntity.class)))
                .thenReturn(userUpdated);

        // Invoke method
        UserDto result = underTest.updateUser(userDtoWithNewData, emailOfRequester);

        // Assertions
        assertEquals(userUpdated, result);
        assertEquals(emailOfRequester, stringArgumentCaptor.getAllValues().getFirst());
        assertEquals(userSaved.getName(), userUpdated.getName());
        assertEquals(userSaved.getEmail(), userUpdated.getEmail());
        assertEquals(userSaved.getPassword(), userUpdated.getPassword());
        assertEquals(userSaved.getPhone(), userUpdated.getPhone());
    }

    @Test
    void testThatUpdateUserThrowsNotFoundExceptionWhenUserNotFound() {
        // Data for test
        UserDto userDtoWithNewData = UserDtoTestData.createTestUserDtoToUpdating();
        String emailOfRequester = userDtoWithNewData.getEmail();
        when(userRepository.findByEmail(stringArgumentCaptor.capture()))
                .thenReturn(Optional.empty());

        // Invoke method (and assertion at the same time)
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> underTest.updateUser(userDtoWithNewData, emailOfRequester)
        );

        // Assertions
        assertEquals("user not found", exception.getMessage());
        assertEquals(emailOfRequester, stringArgumentCaptor.getValue());
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void testThatUpdateUserThrowsAlreadyExistsExceptionByPhone() {
        // Test for data
        UserDto userDtoWithNewData = UserDtoTestData.createTestUserDtoToUpdating();
        UserEntity userSaved = UserEntityTestData.createTestUserEntityCitizen();
        UserEntity citizenWithTheSamePhone = UserEntityTestData.createTestUserEntityCitizenWithTheSamePhone();
        String emailOfRequester = userSaved.getEmail();
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(userSaved));
        when(userRepository.findByPhone(stringArgumentCaptor.capture()))
                .thenReturn(Optional.of(citizenWithTheSamePhone));

        // Invoke method (and assertion at the same time)
        AlreadyExistsException exception = assertThrows(
                AlreadyExistsException.class,
                () -> underTest.updateUser(userDtoWithNewData, emailOfRequester)
        );

        // Assertions
        assertEquals("user with this phone already exists", exception.getMessage());
        assertEquals(userDtoWithNewData.getPhone(), stringArgumentCaptor.getValue());
        verify(userRepository, times(1)).findByEmail(emailOfRequester);
    }

    @Test
    void testThatUpdateUserThrowsAlreadyExistsExceptionByEmail() {
        // Test for data
        UserDto userDtoWithNewData = UserDtoTestData.createTestUserDtoToUpdating();
        UserEntity userSaved = UserEntityTestData.createTestUserEntityCitizen();
        UserEntity citizenWithTheSameEmail = UserEntityTestData.createTestUserEntityCitizenWithTheSameEmail();
        String emailOfRequester = userSaved.getEmail();
        when(userRepository.findByEmail(stringArgumentCaptor.capture()))
                .thenReturn(Optional.of(userSaved))
                .thenReturn(Optional.of(citizenWithTheSameEmail));
        when(userRepository.findByPhone(anyString()))
                .thenReturn(Optional.empty());

        // Invoke method (and assertion at the same time)
        AlreadyExistsException exception = assertThrows(
                AlreadyExistsException.class,
                () -> underTest.updateUser(userDtoWithNewData, emailOfRequester)
        );

        // Assertions
        assertEquals("user with this email already exists", exception.getMessage());
        assertEquals(userDtoWithNewData.getEmail(), stringArgumentCaptor.getAllValues().get(1));
        verify(userRepository, times(2)).findByEmail(anyString());
    }

    @Test
    void testThatDeleteUserThrowsNotFoundExceptionById() {
        // Data for test
        long userId = 1L;
        String emailOfRequester = "anyEmail";
        when(userRepository.findById(longArgumentCaptor.capture()))
                .thenReturn(Optional.empty());

        // Invoke method (and assertion at the same time)
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> underTest.deleteUser(userId, emailOfRequester)
        );

        // Assertions
        assertEquals("user not found", exception.getMessage());
        assertEquals(userId, longArgumentCaptor.getValue());
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void testThatDeleteUserThrowsNotFoundExceptionByEmail() {
        // Data for test
        long userId = 1L;
        String emailOfRequester = "anyEmail";
        UserEntity userToDelete = UserEntityTestData.createTestUserEntityCitizen();
        when(userRepository.findById(longArgumentCaptor.capture()))
                .thenReturn(Optional.of(userToDelete));
        when(userRepository.findByEmail(stringArgumentCaptor.capture()))
                .thenReturn(Optional.empty());

        // Invoke method (and assertion at the same time)
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> underTest.deleteUser(userId, emailOfRequester)
        );

        // Assertions
        assertEquals("user not found", exception.getMessage());
        assertEquals(userId, longArgumentCaptor.getValue());
        assertEquals(emailOfRequester, stringArgumentCaptor.getValue());
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void testThatDeleteUserThrowsActionNotAllowedExceptionByAdmin() {
        // Data for test
        UserEntity userToDelete = UserEntityTestData.createTestUserEntityCitizen();
        UserEntity userRequester = UserEntityTestData.createTestUserEntityAdmin();
        long userToDeleteId = 1L;
        String emailOfRequester = userRequester.getEmail();
        when(userRepository.findById(longArgumentCaptor.capture()))
                .thenReturn(Optional.of(userToDelete));
        when(userRepository.findByEmail(stringArgumentCaptor.capture()))
                .thenReturn(Optional.of(userRequester));

        // Invoke method (and assertion at the same time)
        ActionNotAllowedException exception = assertThrows(
                ActionNotAllowedException.class,
                () -> underTest.deleteUser(userToDeleteId, emailOfRequester)
        );

        // Assertions
        assertEquals("deleting this user is not allowed", exception.getMessage());
        assertEquals(userToDeleteId, longArgumentCaptor.getValue());
        assertEquals(emailOfRequester, stringArgumentCaptor.getValue());
        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void testThatDeleteUserThrowsActionNotAllowedExceptionByCitizen() {
        // Data for test
        UserEntity userToDelete = UserEntityTestData.createTestUserEntityBison();
        UserEntity userRequester = UserEntityTestData.createTestUserEntityCitizen();
        long userToDeleteId = 1L;
        String emailOfRequester = userRequester.getEmail();
        when(userRepository.findById(longArgumentCaptor.capture()))
                .thenReturn(Optional.of(userToDelete));
        when(userRepository.findByEmail(stringArgumentCaptor.capture()))
                .thenReturn(Optional.of(userRequester));

        // Invoke method (and assertion at the same time)
        ActionNotAllowedException exception = assertThrows(
                ActionNotAllowedException.class,
                () -> underTest.deleteUser(userToDeleteId, emailOfRequester)
        );

        // Assertions
        assertEquals("deleting this user is not allowed", exception.getMessage());
        assertEquals(userToDeleteId, longArgumentCaptor.getValue());
        assertEquals(emailOfRequester, stringArgumentCaptor.getValue());
        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void testThatDeleteUserIsSuccessful() {
        // Data for test
        UserEntity userRequester = UserEntityTestData.createTestUserEntityCitizen1();
        UserEntity userToDelete = UserEntityTestData.createTestUserEntityCitizen1();
        long userToDeleteId = 1L;
        String emailOfRequester = userRequester.getEmail();
        when(userRepository.findById(longArgumentCaptor.capture()))
                .thenReturn(Optional.of(userToDelete));
        when(userRepository.findByEmail(stringArgumentCaptor.capture()))
                .thenReturn(Optional.of(userRequester));
        doNothing().when(userRepository).deleteById(longArgumentCaptor.capture());

        // Invoke method
        underTest.deleteUser(userToDeleteId, emailOfRequester);

        // Assertions
        assertEquals(userToDeleteId, longArgumentCaptor.getAllValues().getFirst());
        assertEquals(userToDeleteId, longArgumentCaptor.getAllValues().get(1));
        assertEquals(emailOfRequester, stringArgumentCaptor.getValue());
        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(userRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void getUserByEmailSuccessfullyReturnsUserDto() {
        // Data for test
        UserEntity userEntityRetrieved = UserEntityTestData.createTestUserEntityCitizen();
        UserDto userDtoRetrieved = UserDtoTestData.createTestUserDtoCitizen();
        when(userRepository.findByEmail(stringArgumentCaptor.capture()))
                .thenReturn(Optional.of(userEntityRetrieved));
        when(userMapper.mapToDto(userEntityArgumentCaptor.capture()))
                .thenReturn(userDtoRetrieved);

        // Invoke method
        UserDto result = underTest.getUserByEmail(userEntityRetrieved.getEmail());

        // Assertions
        assertEquals(userDtoRetrieved, result);
        assertEquals(userDtoRetrieved.getEmail(), stringArgumentCaptor.getValue());
        assertEquals(userEntityRetrieved, userEntityArgumentCaptor.getValue());
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void getUserByEmailThrowsNotFoundExceptionByEmail() {
        // Data for test
        String userEmail = "anyEmail";
        when(userRepository.findByEmail(stringArgumentCaptor.capture()))
                .thenReturn(Optional.empty());

        // Invoke method (and assertion at the same time)
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> underTest.getUserByEmail(userEmail)
        );

        // Assertions
        assertEquals("user not found", exception.getMessage());
        assertEquals(userEmail, stringArgumentCaptor.getValue());
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void testThatGetUserByRoleThrowsNotFoundExceptionByRole() {
        // Data for test
        String roleName = "CITIZEN";
        when(roleRepository.findByName(stringArgumentCaptor.capture()))
                .thenReturn(Optional.empty());

        // Invoke method (and assertion at the same time)
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> underTest.getUserByRole(roleName)
        );

        // Assertions
        assertEquals("role not found", exception.getMessage());
        assertEquals("ROLE_"+roleName, stringArgumentCaptor.getValue());
        verify(roleRepository, times(1)).findByName(anyString());
    }

    @Test
    void testThatGetUserByRoleSuccessfullyReturnsListOfUserDto() {
        // Data for test
        String roleName = "CITIZEN";
        UserDto userDto = UserDtoTestData.createTestUserDtoCitizen();
        UserDto userDto1 = UserDtoTestData.createTestUserDtoCitizen1();
        RoleEntity roleRetrieved = RoleEntityTestData.createTestRoleEntityCitizen();
        when(roleRepository.findByName(stringArgumentCaptor.capture()))
                .thenReturn(Optional.of(roleRetrieved));
        when(userMapper.mapToDto(userEntityArgumentCaptor.capture()))
                .thenReturn(userDto, userDto1);

        // Invoke method
        List<UserDto> result = underTest.getUserByRole(roleName);

        // Assertions
        assertEquals(List.of(userDto, userDto1), result);
        assertEquals("ROLE_"+roleName, stringArgumentCaptor.getValue());
        assertEquals(roleRetrieved.getUsers(), userEntityArgumentCaptor.getAllValues());
        verify(roleRepository, times(1)).findByName(anyString());
    }

    @Test
    void testThatGetUserServicesThrowsNotFoundExceptionByUser() {
        // Data for test
        String userEmail = "anyEmail";
        when(userRepository.findByEmail(stringArgumentCaptor.capture()))
                .thenReturn(Optional.empty());

        // Invoke method (and assertion at the same time)
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> underTest.getUserServices(userEmail, null)
        );

        // Assertions
        assertEquals("user not found", exception.getMessage());
        assertEquals(userEmail, stringArgumentCaptor.getValue());
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void testThatGetUserSuccessfullyReturnsListOfServiceDtoForCitizen() {
        // Data for test
        UserEntity userCitizen = UserEntityTestData.createTestUserEntityCitizen();
        String serviceType = "carriage";
        ServiceDto serviceExpected = ServiceDtoTestData.createTestServiceDtoCarriage();
        when(userRepository.findByEmail(stringArgumentCaptor.capture()))
                .thenReturn(Optional.of(userCitizen));
        when(serviceMapper.mapToDto(any(ServiceEntity.class)))
                .thenReturn(serviceExpected)
                .thenReturn(ServiceDtoTestData.createTestServiceDtoPackage());

        // Invoke method
        List<ServiceDto> result = underTest.getUserServices(userCitizen.getEmail(), serviceType);

        // Assertions;
        assertEquals(List.of(serviceExpected), result);
        assertEquals(userCitizen.getEmail(), stringArgumentCaptor.getValue());
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void testThatGetUserSuccessfullyReturnsListOfServiceDtoForBison() {
        // Data for test
        UserEntity userBison = UserEntityTestData.createTestUserEntityBison();
        String serviceType = "package";
        ServiceDto serviceExpected = ServiceDtoTestData.createTestServiceDtoPackage();
        when(userRepository.findByEmail(stringArgumentCaptor.capture()))
                .thenReturn(Optional.of(userBison));
        when(serviceMapper.mapToDto(any(ServiceEntity.class)))
                .thenReturn(ServiceDtoTestData.createTestServiceDtoCarriage())
                .thenReturn(serviceExpected);

        // Invoke method
        List<ServiceDto> result = underTest.getUserServices(userBison.getEmail(), serviceType);

        // Assertions
        assertEquals(List.of(serviceExpected), result);
        assertEquals(userBison.getEmail(), stringArgumentCaptor.getValue());
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void testThatGetLastServiceThrowsNotFoundExceptionByUser() {
        // Data for test
        String userEmail = "anyEmail";
        when(userRepository.findByEmail(stringArgumentCaptor.capture()))
                .thenReturn(Optional.empty());

        // Invoke method (and assertion at the same time)
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> underTest.getLastService(userEmail)
        );

        // Assertions
        assertEquals("user not found", exception.getMessage());
        assertEquals(userEmail, stringArgumentCaptor.getValue());
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void testThatGetLastServiceSuccessfullyReturnsServiceDto() {
        // Data for test
        UserEntity userCitizen = UserEntityTestData.createTestUserEntityCitizen();
        ServiceEntity serviceEntityExpected = userCitizen.getCitizenOrders().getFirst();
        ServiceDto serviceDtoExpected = ServiceDtoTestData.createTestServiceDtoCarriage();
        when(userRepository.findByEmail(stringArgumentCaptor.capture()))
                .thenReturn(Optional.of(userCitizen));
        when(serviceMapper.mapToDto(serviceEntityArgumentCaptor.capture()))
                .thenReturn(serviceDtoExpected);

        // Invoke method
        ServiceDto result = underTest.getLastService(userCitizen.getEmail());

        // Assertions
        assertEquals(serviceDtoExpected, result);
        assertEquals(userCitizen.getEmail(), stringArgumentCaptor.getValue());
        assertEquals(serviceEntityExpected, serviceEntityArgumentCaptor.getValue());
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void testThatGetLastServiceThrowsNotFoundExceptionByService() {
        // Data for test
        UserEntity userCitizen = UserEntityTestData.createTestUserEntityCitizenWithOutServices();
        when(userRepository.findByEmail(stringArgumentCaptor.capture()))
                .thenReturn(Optional.of(userCitizen));

        // Invoke method (and assertion at the same time)
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> underTest.getLastService(userCitizen.getEmail())
        );

        // Assertions
        assertEquals("service not found", exception.getMessage());
        assertEquals(userCitizen.getEmail(), stringArgumentCaptor.getValue());
        verifyNoInteractions(serviceMapper);
    }

    /**
     * NotFoundException("user")
     * successfully returns serviceDto
     * NotFoundException("service")
     */
    @Test
    void testThatGetActiveServiceThrowsNotFoundExceptionByUser() {
        // Data for test
        String userEmail = "anyEmail";
        when(userRepository.findByEmail(stringArgumentCaptor.capture()))
                .thenReturn(Optional.empty());

        // Invoke method (and assertion at the same time)
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> underTest.getActiveService(userEmail)
        );

        // Assertions
        assertEquals("user not found", exception.getMessage());
        assertEquals(userEmail, stringArgumentCaptor.getValue());
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void testThatGetActiveServiceSuccessfullyReturnsServiceDto() {
        // Data for test
        UserEntity userBison = UserEntityTestData.createTestUserEntityBison();
        ServiceDto serviceExpected = ServiceDtoTestData.createTestServiceDtoPackage();
        when(userRepository.findByEmail(stringArgumentCaptor.capture()))
                .thenReturn(Optional.of(userBison));
        when(serviceUseCase.getActiveService(longArgumentCaptor.capture()))
                .thenReturn(Optional.of(serviceExpected));

        // Invoke method
        ServiceDto result = underTest.getActiveService(userBison.getEmail());

        // Assertions
        assertEquals(serviceExpected, result);
        assertEquals(userBison.getEmail(), stringArgumentCaptor.getValue());
        assertEquals(userBison.getId(), longArgumentCaptor.getValue());
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void testThatGetActiveServiceThrowsNotFoundExceptionByService() {
        // Data for test
        UserEntity userBison = UserEntityTestData.createTestUserEntityBison();
        when(userRepository.findByEmail(stringArgumentCaptor.capture()))
                .thenReturn(Optional.of(userBison));
        when(serviceUseCase.getActiveService(longArgumentCaptor.capture()))
                .thenReturn(Optional.empty());

        // Invoke method (and assertion at the same time)
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> underTest.getActiveService(userBison.getEmail())
        );

        // Assertions
        assertEquals("service not found", exception.getMessage());
        assertEquals(userBison.getEmail(), stringArgumentCaptor.getValue());
        assertEquals(userBison.getId(), longArgumentCaptor.getValue());
        verify(userRepository, times(1)).findByEmail(anyString());
    }
}