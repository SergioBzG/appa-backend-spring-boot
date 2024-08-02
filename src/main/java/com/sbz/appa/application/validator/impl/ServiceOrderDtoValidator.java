package com.sbz.appa.application.validator.impl;

import com.sbz.appa.application.dto.ServiceOrderDto;
import com.sbz.appa.application.exception.InvalidOrMissingDataException;
import com.sbz.appa.application.validator.Validator;
import com.sbz.appa.commons.ServiceType;
import org.springframework.stereotype.Component;

@Component
public class ServiceOrderDtoValidator implements Validator<ServiceOrderDto> {

    @Override
    public boolean validate(ServiceOrderDto serviceOrder) {
        if (serviceOrder.getType().equals(ServiceType.CARRIAGE.name()))
            return true;
        return hasLength(serviceOrder)
                && hasWidth(serviceOrder)
                && hasHeight(serviceOrder)
                && hasWeight(serviceOrder);
    }

    private boolean hasLength(ServiceOrderDto serviceOrder) {
        if (serviceOrder.getLength() != null)
            return true;
        throw new InvalidOrMissingDataException("package", "length");
    }

    private boolean hasWidth(ServiceOrderDto serviceOrder) {
        if (serviceOrder.getWidth() != null)
            return true;
        throw new InvalidOrMissingDataException("package", "width");
    }

    private boolean hasHeight(ServiceOrderDto serviceOrder) {
        if (serviceOrder.getHeight() != null)
            return true;
        throw new InvalidOrMissingDataException("package", "height");
    }

    private boolean hasWeight(ServiceOrderDto serviceOrder) {
        if (serviceOrder.getWeight() != null)
            return true;
        throw new InvalidOrMissingDataException("package", "weight");
    }
}
