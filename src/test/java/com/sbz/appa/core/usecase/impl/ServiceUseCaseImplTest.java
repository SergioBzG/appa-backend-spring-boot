package com.sbz.appa.core.usecase.impl;

import com.sbz.appa.application.dto.*;
import com.sbz.appa.application.exception.ActionNotAllowedException;
import com.sbz.appa.application.exception.InvalidOrMissingDataException;
import com.sbz.appa.application.exception.NotFoundException;
import com.sbz.appa.commons.Checkpoint;
import com.sbz.appa.core.domain.model.ServiceOrder;
import com.sbz.appa.core.mapper.Mapper;
import com.sbz.appa.core.mapper.ServiceOrderDtoToServiceOrder;
import com.sbz.appa.infrastructure.persistence.entity.GuideEntity;
import com.sbz.appa.infrastructure.persistence.entity.ServiceEntity;
import com.sbz.appa.infrastructure.persistence.entity.UserEntity;
import com.sbz.appa.infrastructure.persistence.repository.ServiceRepository;
import com.sbz.appa.infrastructure.persistence.repository.UserRepository;
import com.sbz.appa.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceUseCaseImplTest {

    @Mock
    private ServiceRepository serviceRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private Mapper<ServiceEntity, ServiceDto> serviceMapper;
    @Mock
    private Mapper<GuideEntity, GuideDto> guideMapper;
    @Mock
    private ServiceOrderDtoToServiceOrder serviceOrderDtoToServiceOrder;

    private ServiceUseCaseImpl underTest;

    @Captor
    ArgumentCaptor<ServiceDto> serviceDtoArgumentCaptor;
    @Captor
    ArgumentCaptor<ServiceEntity> serviceEntityArgumentCaptor;
    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;
    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;
    @Captor
    ArgumentCaptor<Checkpoint> checkpointArgumentCaptor;
    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @BeforeEach
    void setUp() {
        underTest = new ServiceUseCaseImpl(
                serviceRepository,
                userRepository,
                serviceMapper,
                guideMapper,
                serviceOrderDtoToServiceOrder
        );
    }

    @Test
    void testThatSaveServiceThrowsNotFoundException() {
        // Data for test
        ServiceDto serviceToSave = ServiceDtoTestData.createTestServiceDtoCarriage();
        String userEmail = "anyUser";
        when(userRepository.findByEmail(stringArgumentCaptor.capture()))
                .thenReturn(Optional.empty());

        // Invoke method (and assertion at the same time)
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> underTest.saveService(serviceToSave, userEmail)
        );

        // Assertions
        assertEquals("user not found", exception.getMessage());
        assertEquals(userEmail, stringArgumentCaptor.getValue());
        verifyNoInteractions(serviceMapper);
    }

    @Test
    void testThatSaveServiceSuccessfullyReturnsServiceDtoForCarriage() {
        // Data for test
        ServiceDto serviceToSave = ServiceDtoTestData.createTestServiceDtoCarriage();
        UserEntity userEntity = UserEntityTestData.createTestUserEntityCitizen();
        ServiceEntity serviceEntity = ServiceEntityTestData.createTestServiceEntityCarriage();
        when(userRepository.findByEmail(stringArgumentCaptor.capture()))
                .thenReturn(Optional.of(userEntity));
        when(serviceMapper.mapFromDto(serviceDtoArgumentCaptor.capture()))
                .thenReturn(serviceEntity);
        when(serviceMapper.mapToDto(serviceEntityArgumentCaptor.capture()))
                .thenReturn(serviceToSave);
        when(serviceRepository.save(any(ServiceEntity.class)))
                .thenReturn(serviceEntity);

        // Invoke method
        ServiceDto result = underTest.saveService(serviceToSave, userEntity.getEmail());

        // Assertions
        assertEquals(serviceToSave, result);
        assertEquals(userEntity.getEmail(), stringArgumentCaptor.getValue());
        assertEquals(serviceToSave, serviceDtoArgumentCaptor.getValue());
        assertEquals(serviceEntity, serviceEntityArgumentCaptor.getValue());
    }

    @Test
    void testThatSaveServiceSuccessfullyReturnsServiceDtoForPackage() {
        // Data for test
        ServiceDto serviceToSave = ServiceDtoTestData.createTestServiceDtoPackage();
        UserEntity userEntity = UserEntityTestData.createTestUserEntityCitizen();
        ServiceEntity serviceEntity = ServiceEntityTestData.createTestServiceEntityPackage();
        when(userRepository.findByEmail(stringArgumentCaptor.capture()))
                .thenReturn(Optional.of(userEntity));
        when(serviceMapper.mapFromDto(serviceDtoArgumentCaptor.capture()))
                .thenReturn(serviceEntity);
        when(serviceMapper.mapToDto(serviceEntityArgumentCaptor.capture()))
                .thenReturn(serviceToSave);
        when(serviceRepository.save(any(ServiceEntity.class)))
                .thenReturn(serviceEntity);

        // Invoke method
        ServiceDto result = underTest.saveService(serviceToSave, userEntity.getEmail());

        // Assertions
        assertEquals(serviceToSave, result);
        assertEquals(userEntity.getEmail(), stringArgumentCaptor.getValue());
        assertEquals(serviceToSave, serviceDtoArgumentCaptor.getValue());
        assertEquals(serviceEntity, serviceEntityArgumentCaptor.getValue());
    }

    @Test
    void testThatUpdateServiceThrowsNotFoundExceptionByServiceId() {
        // Data for test
        long id = 1L;
        GuideDto newLocation = GuideDtoTestData.createTestGuideDto();
        String userEmail = "anyEmail";
        double price = 234.332d;
        when(serviceRepository.findById(longArgumentCaptor.capture()))
                .thenReturn(Optional.empty());

        // Invoke method (and assertion at the same time)
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> underTest.updateService(id, newLocation, userEmail, price)
        );

        // Assertions
        assertEquals("service not found", exception.getMessage());
        assertEquals(id, longArgumentCaptor.getValue());
        verifyNoInteractions(guideMapper);
    }

    @Test
    void testThatUpdateServiceThrowsNotFoundExceptionByBison() {
        // Data for test
        long id = 1L;
        GuideDto newLocation = GuideDtoTestData.createTestGuideDto();
        String userEmail = "anyEmail";
        double price = 234.332d;
        ServiceEntity serviceEntityToUpdate = ServiceEntityTestData.createTestServiceEntityCarriage();
        GuideEntity guideEntity = GuideEntityTestData.createTestGuideEntity();
        when(serviceRepository.findById(longArgumentCaptor.capture()))
                .thenReturn(Optional.of(serviceEntityToUpdate));
        when(guideMapper.mapFromDto(any(GuideDto.class)))
                .thenReturn(guideEntity);

        // Invoke method (and assertion at the same time)
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> underTest.updateService(id, newLocation, userEmail, price)
        );

        // Assertions
        assertEquals("service not found", exception.getMessage());
        assertEquals(id, longArgumentCaptor.getValue());
        verify(guideMapper, times(1)).mapFromDto(newLocation);
    }

    @Test
    void testThatUpdateServiceThrowsActionNotAllowedException() {
        // Data for test
        long id = 1L;
        GuideDto newLocation = GuideDtoTestData.createTestGuideDto();
        double price = 234.332d;
        ServiceEntity serviceEntityToUpdate =
                ServiceEntityTestData.createTestServiceEntityPackageWithBison();
        GuideEntity guideEntity = GuideEntityTestData.createTestGuideEntity();
        String userEmail = serviceEntityToUpdate.getUserBison().getEmail();
        when(serviceRepository.findById(longArgumentCaptor.capture()))
                .thenReturn(Optional.of(serviceEntityToUpdate));
        when(guideMapper.mapFromDto(any(GuideDto.class)))
                .thenReturn(guideEntity);

        // Invoke method (and assertion at the same time)
        ActionNotAllowedException exception = assertThrows(
                ActionNotAllowedException.class,
                () -> underTest.updateService(id, newLocation, userEmail, price)
        );

        // Assertions
        assertEquals("updating this service is not allowed", exception.getMessage());
        assertEquals(id, longArgumentCaptor.getValue());
        verify(guideMapper, times(1)).mapFromDto(newLocation);
    }

    @Test
    void testThatUpdateServiceThrowsInvalidOrMissingDataExceptionWhenPriceIsNull() {
        // Data for test
        long id = 1L;
        GuideDto newLocation = GuideDtoTestData.createTestGuideDto();
        ServiceEntity serviceEntityToUpdate =
                ServiceEntityTestData.createTestServiceEntityCarriageWithBisonAndArrivedNull();
        GuideEntity guideEntity = GuideEntityTestData.createTestGuideEntity();
        String userEmail = serviceEntityToUpdate.getUserBison().getEmail();
        when(serviceRepository.findById(longArgumentCaptor.capture()))
                .thenReturn(Optional.of(serviceEntityToUpdate));
        when(guideMapper.mapFromDto(any(GuideDto.class)))
                .thenReturn(guideEntity);

        // Invoke method (and assertion at the same time)
        InvalidOrMissingDataException exception = assertThrows(
                InvalidOrMissingDataException.class,
                () -> underTest.updateService(id, newLocation, userEmail, null)
        );

        // Assertions
        assertEquals("invalid or missing price in carriage", exception.getMessage());
        assertEquals(id, longArgumentCaptor.getValue());
        verify(guideMapper, times(1)).mapFromDto(newLocation);
    }

    @Test
    void testThatUpdateServiceSuccessfullyReturnsServiceDto() {
        // Data for test
        long id = 1L;
        double price = 234.332d;
        GuideDto newLocation = GuideDtoTestData.createTestGuideDto();
        ServiceEntity serviceEntityToUpdate =
                ServiceEntityTestData.createTestServiceEntityCarriageWithBisonAndArrivedNull();
        GuideEntity guideEntity = GuideEntityTestData.createTestGuideEntity();
        String userEmail = serviceEntityToUpdate.getUserBison().getEmail();
        ServiceDto serviceDtoUpdated = ServiceDtoTestData.createTestServiceDtoCarriage();
        when(serviceRepository.findById(longArgumentCaptor.capture()))
                .thenReturn(Optional.of(serviceEntityToUpdate));
        when(guideMapper.mapFromDto(any(GuideDto.class)))
                .thenReturn(guideEntity);
        when(serviceMapper.mapToDto(serviceEntityToUpdate))
                .thenReturn(serviceDtoUpdated);

        // Invoke method
        ServiceDto result = underTest.updateService(id, newLocation, userEmail, price);

        // Assertions
        assertEquals(serviceDtoUpdated, result);
        assertNotNull(serviceEntityToUpdate.getArrived());
        assertNotNull(serviceEntityToUpdate.getUserBison().getLastDelivery());
        assertEquals(id, longArgumentCaptor.getValue());
    }

    @Test
    void testThatGetServiceThrowsNotFoundExceptionByUser() {
        // Data for test
        long id = 1L;
        String userEmail = "anyUser";
        when(userRepository.findByEmail(stringArgumentCaptor.capture()))
                .thenReturn(Optional.empty());

        // Invoke method (and assertion at the same time)
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> underTest.getService(id, userEmail)
        );

        // Assertions
        assertEquals("user not found", exception.getMessage());
        assertEquals(userEmail, stringArgumentCaptor.getValue());
        verifyNoInteractions(serviceRepository);
    }

    @Test
    void testThatGetServiceThrowsNotFoundExceptionByService() {
        // Data for test
        long id = 1L;
        UserEntity userEntity = UserEntityTestData.createTestUserEntityBison();
        when(userRepository.findByEmail(stringArgumentCaptor.capture()))
                .thenReturn(Optional.of(userEntity));
        when(serviceRepository.findById(longArgumentCaptor.capture()))
                .thenReturn(Optional.empty());

        // Invoke method (and assertion at the same time)
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> underTest.getService(id, userEntity.getEmail())
        );

        // Assertions
        assertEquals("service not found", exception.getMessage());
        assertEquals(userEntity.getEmail(), stringArgumentCaptor.getValue());
        assertEquals(id, longArgumentCaptor.getValue());
    }

    @Test
    void testThatGetServiceThrowsNotFoundExceptionByBison() {
        // Data for test
        long id = 1L;
        UserEntity userEntity = UserEntityTestData.createTestUserEntityBison();
        ServiceEntity serviceEntity = ServiceEntityTestData.createTestServiceEntityPackage();
        when(userRepository.findByEmail(stringArgumentCaptor.capture()))
                .thenReturn(Optional.of(userEntity));
        when(serviceRepository.findById(longArgumentCaptor.capture()))
                .thenReturn(Optional.of(serviceEntity));

        // Invoke method (and assertion at the same time)
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> underTest.getService(id, userEntity.getEmail())
        );

        // Assertions
        assertEquals("service not found", exception.getMessage());
        assertEquals(userEntity.getEmail(), stringArgumentCaptor.getValue());
        assertEquals(id, longArgumentCaptor.getValue());
    }

    @Test
    void testThatGetServiceThrowsNotFoundExceptionByCitizen() {
        // Data for test
        long id = 1L;
        UserEntity userEntity = UserEntityTestData.createTestUserEntityCitizen1();
        ServiceEntity serviceEntity = ServiceEntityTestData.createTestServiceEntityCarriageWithCitizen();
        when(userRepository.findByEmail(stringArgumentCaptor.capture()))
                .thenReturn(Optional.of(userEntity));
        when(serviceRepository.findById(longArgumentCaptor.capture()))
                .thenReturn(Optional.of(serviceEntity));

        // Invoke method (and assertion at the same time)
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> underTest.getService(id, userEntity.getEmail())
        );

        // Assertions
        assertEquals("service not found", exception.getMessage());
        assertEquals(userEntity.getEmail(), stringArgumentCaptor.getValue());
        assertEquals(id, longArgumentCaptor.getValue());
    }

    // TODO: to search how to mock abstract classes
    /**
     * The following two tests does not work.
     * It seems that the ServiceOrder mock is wrong.
     */
//    @Test
    void testThatGetServicePriceReturnsPrice() {
        // Data for test
        ServiceOrder serviceOrderMock = mock(ServiceOrder.class, Answers.RETURNS_MOCKS);
        ServiceOrderDto serviceOrderDto = ServiceOrderDtoTestData.createTestServiceOrderDto();
        ServiceOrder serviceOrderCreated = ServiceOrderTestData.createTestServiceOrderCarriage();
        double price = 234.45d;
        when(serviceOrderDtoToServiceOrder.mapFromDto(any(ServiceOrderDto.class)))
                .thenReturn(serviceOrderCreated);
        when(serviceOrderMock.getPrice())
                .thenReturn(price);

        // Invoke method
        Double result = underTest.getServicePrice(serviceOrderDto);

        // Assertions
        assertEquals(price, result);
        verify(serviceOrderDtoToServiceOrder, times(1)).mapFromDto(serviceOrderDto);
    }

//    @Test
    void testThatGetOptimalRouteReturnsRouteDto() {
        // Data for test
        ServiceOrder serviceOrderMock = mock(ServiceOrder.class, Answers.CALLS_REAL_METHODS);
        PathDto pathDto = PathDtoTestData.createTestPathDto();
        RouteDto routeDto = RouteDtoTestData.createTestRouteDto();
        when(serviceOrderMock.getPathList(checkpointArgumentCaptor.capture(), checkpointArgumentCaptor.capture()))
                .thenReturn(routeDto.getOptimalRoute());

        // Invoke method
        RouteDto result = underTest.getOptimalRoute(pathDto);

        // Assertions
        assertEquals(routeDto, result);
        assertEquals(Checkpoint.valueOf(pathDto.getOriginCheckpoint()),
                checkpointArgumentCaptor.getAllValues().getFirst());
        assertEquals(Checkpoint.valueOf(pathDto.getDestinationCheckpoint()),
                checkpointArgumentCaptor.getAllValues().get(1));
    }

    @Test
    void testThatTrackServiceThrowsNotFoundExceptionByUuid() {
        // Data for test
        UUID guideId = UUID.randomUUID();
        String userEmail = "anyEmail";
        when(serviceRepository.findByGuideId(uuidArgumentCaptor.capture()))
                .thenReturn(Optional.empty());

        // Invoke method (and assertion at the same time)
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> underTest.trackService(guideId, userEmail)
        );

        // Assertions
        assertEquals("service not found", exception.getMessage());
        assertEquals(guideId, uuidArgumentCaptor.getValue());
        verifyNoInteractions(guideMapper);
    }

    @Test
    void testThatTrackServiceThrowsNotFoundExceptionByCitizen() {
        // Data for test
        GuideDto guideDto = GuideDtoTestData.createTestGuideDto();
        ServiceEntity serviceEntity = ServiceEntityTestData.createTestServiceEntityCarriageWithCitizen();
        String userEmail = "anyEmail";
        when(serviceRepository.findByGuideId(uuidArgumentCaptor.capture()))
                .thenReturn(Optional.of(serviceEntity));

        // Invoke method (and assertion at the same time)
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> underTest.trackService(guideDto.getId(), userEmail)
        );

        // Assertions
        assertEquals("service not found", exception.getMessage());
        assertEquals(guideDto.getId(), uuidArgumentCaptor.getValue());
        verifyNoInteractions(guideMapper);
    }

    @Test
    void testThatTrackServiceReturnsGuideDto() {
        // Data for test
        GuideDto guideDto = GuideDtoTestData.createTestGuideDto();
        ServiceEntity serviceEntity = ServiceEntityTestData.createTestServiceEntityCarriageWithCitizen();
        String userEmail = serviceEntity.getUserCitizen().getEmail();
        when(serviceRepository.findByGuideId(uuidArgumentCaptor.capture()))
                .thenReturn(Optional.of(serviceEntity));
        when(guideMapper.mapToDto(serviceEntity.getGuide()))
                .thenReturn(guideDto);

        // Invoke method
        GuideDto result = underTest.trackService(guideDto.getId(), userEmail);

        // Assertions
        assertEquals(guideDto, result);
        assertEquals(guideDto.getId(), uuidArgumentCaptor.getValue());
        verify(guideMapper, times(1)).mapToDto(serviceEntity.getGuide());
    }

    @Test
    void testThatGetActiveServiceReturnsServiceDto() {
        // Data for test
        Long bisonId = 1L;
        ServiceEntity serviceEntity = ServiceEntityTestData.createTestServiceEntityPackage();
        ServiceDto serviceDto = ServiceDtoTestData.createTestServiceDtoPackage();
        when(serviceRepository.findFirstByArrivedIsNullAndUserBisonId(longArgumentCaptor.capture()))
                .thenReturn(Optional.of(serviceEntity));
        when(serviceMapper.mapToDto(serviceEntity))
                .thenReturn(serviceDto);

        // Invoke method
        Optional<ServiceDto> result = underTest.getActiveService(bisonId);

        // Assertions
        assertTrue(result.isPresent());
        assertEquals(serviceDto, result.get());
        assertEquals(bisonId, longArgumentCaptor.getValue());
    }

    @Test
    void searchForOrder() {
        // Data for test

        // Invoke method

        // Assertions
    }

    @Test
    void searchForBison() {
        // Data for test

        // Invoke method

        // Assertions
    }
}


























