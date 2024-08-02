package com.sbz.appa.application.validator.impl;

import com.sbz.appa.application.dto.ServiceDto;
import com.sbz.appa.application.exception.InvalidOrMissingDataException;
import com.sbz.appa.application.validator.Validator;
import com.sbz.appa.commons.ServiceType;
import org.springframework.stereotype.Component;

@Component
public class ServiceDtoValidator implements Validator<ServiceDto> {

    @Override
    public boolean validate(ServiceDto service) {
        if (service.getType().equals(ServiceType.CARRIAGE.name()) && service.getCarriageDto() != null)
            return true;
        else if (service.getType().equals(ServiceType.PACKAGE.name()) && service.getPackageDto() != null)
            return true;
        throw new InvalidOrMissingDataException("service", "type");
    }
}
