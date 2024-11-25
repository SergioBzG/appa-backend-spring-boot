package com.sbz.appa.application.controller;

import com.sbz.appa.application.dto.*;
import com.sbz.appa.application.util.ServicePrice;
import com.sbz.appa.application.validator.Validator;
import com.sbz.appa.core.usecase.ServiceUseCase;
import com.sbz.appa.util.PathDtoTestData;
import com.sbz.appa.util.RouteDtoTestData;
import com.sbz.appa.util.ServiceDtoTestData;
import com.sbz.appa.util.ServiceOrderDtoTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ServiceControllerTest {

    @Mock
    private ServiceUseCase serviceUseCase;
    @Mock
    private Validator<ServiceOrderDto> serviceOrderDtoValidator;
    @Mock
    private Validator<ServiceDto> serviceDtoValidator;

    private ServiceController underTest;

    @Captor
    private ArgumentCaptor<ServiceDto> serviceDtoArgumentCaptor;
    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;
    @Captor
    private ArgumentCaptor<Long> longArgumentCaptor;
    @Captor
    private ArgumentCaptor<PathDto> pathDtoArgumentCaptor;

    @BeforeEach
    void setUp() {
        underTest = new ServiceController(serviceUseCase, serviceOrderDtoValidator, serviceDtoValidator);
    }

    @Test
    void testThatCreateServiceSuccessfullyReturnsServiceDto() {
        // Data for test
        Authentication authentication = mock(Authentication.class);
        String userEmail = "anyEmail";
        ServiceDto serviceDtoToCreate = ServiceDtoTestData.createTestServiceDtoCarriage();
        when(authentication.getName())
                .thenReturn(userEmail);
        when(serviceUseCase.saveService(serviceDtoArgumentCaptor.capture(), stringArgumentCaptor.capture()))
                .thenReturn(serviceDtoToCreate);

        // Invoke method
        ResponseEntity<ServiceDto> response = underTest.createService(serviceDtoToCreate, authentication);

        // Assertions
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(serviceDtoToCreate, response.getBody());
        assertEquals(serviceDtoToCreate, serviceDtoArgumentCaptor.getValue());
        assertEquals(userEmail, stringArgumentCaptor.getValue());
        verify(serviceDtoValidator, times(1)).validate(any(ServiceDto.class));
    }

    @Test
    void testThatUpdateServiceSuccessfullyReturnsServiceDto() {
        // Data for test
        Authentication authentication = mock(Authentication.class);
        ServiceDto serviceToUpdate = ServiceDtoTestData.createTestServiceDtoPackage();
        Long serviceId = serviceToUpdate.getId();
        Double price = serviceToUpdate.getPrice();
        GuideDto newLocation = serviceToUpdate.getGuide();
        String userEmail = "anyEmail";
        when(authentication.getName())
                .thenReturn(userEmail);
        when(serviceUseCase.updateService(
                longArgumentCaptor.capture(), any(GuideDto.class), stringArgumentCaptor.capture(), any(Double.class)))
                .thenReturn(serviceToUpdate);

        // Invoke method
        ResponseEntity<ServiceDto> response = underTest.updateService(serviceId, newLocation, price, authentication);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(serviceToUpdate, response.getBody());
        assertEquals(serviceId, longArgumentCaptor.getValue());
        assertEquals(userEmail, stringArgumentCaptor.getValue());
    }

    @Test
    void testThatGetServiceSuccessfullyReturnsServiceDto() {
        // Data for test
        Authentication authentication = mock(Authentication.class);
        ServiceDto serviceToGet = ServiceDtoTestData.createTestServiceDtoCarriage();
        Long serviceId = serviceToGet.getId();
        String userEmail = "anyEmail";
        when(authentication.getName())
                .thenReturn(userEmail);
        when(serviceUseCase.getService(longArgumentCaptor.capture(), stringArgumentCaptor.capture()))
                .thenReturn(serviceToGet);

        // Invoke method
        ResponseEntity<ServiceDto> response = underTest.getService(serviceId, authentication);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(serviceToGet, response.getBody());
        assertEquals(serviceId, longArgumentCaptor.getValue());
        assertEquals(userEmail, stringArgumentCaptor.getValue());
    }

    @Test
    void testThatGetPriceSuccessfullyReturnsServicePrice() {
        // Data for test
        ServiceOrderDto serviceOrderDto = ServiceOrderDtoTestData.createTestServiceOrderDto();
        double price = 100.0;
        when(serviceUseCase.getServicePrice(serviceOrderDto))
                .thenReturn(price);

        // Invoke method
        ResponseEntity<ServicePrice> response = underTest.getPrice(serviceOrderDto);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(price, Objects.requireNonNull(response.getBody()).getPrice());
        verify(serviceOrderDtoValidator, times(1)).validate(serviceOrderDto);
    }

    @Test
    void testThatGetRouteSuccessfullyReturnsRouteDto() {
        // Data for test
        PathDto pathDto = PathDtoTestData.createTestPathDto();
        RouteDto routeDto = RouteDtoTestData.createTestRouteDto();
        when(serviceUseCase.getOptimalRoute(pathDtoArgumentCaptor.capture()))
                .thenReturn(routeDto);
        // Invoke method
        ResponseEntity<RouteDto> response = underTest.getRoute(pathDto);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(routeDto, response.getBody());
        assertEquals(pathDto, pathDtoArgumentCaptor.getValue());
    }

    @Test
    void testThatTrackServiceSuccessfullyReturnsGuideDto() {
        // Data for test
        Authentication authentication = mock(Authentication.class);
        GuideDto guideDto = ServiceDtoTestData.createTestServiceDtoPackage().getGuide();
        String guidId = guideDto.getId().toString();
        String anyEmail = "anyEmail";
        when(authentication.getName())
                .thenReturn(anyEmail);
        when(serviceUseCase.trackService(any(UUID.class), stringArgumentCaptor.capture()))
                .thenReturn(guideDto);

        // Invoke method
        ResponseEntity<GuideDto> response = underTest.trackService(guidId, authentication);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(guideDto, response.getBody());
        assertEquals(anyEmail, stringArgumentCaptor.getValue());
        verify(serviceUseCase, times(1)).trackService(guideDto.getId(), anyEmail);
    }
}