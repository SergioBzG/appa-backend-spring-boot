package com.sbz.appa.core.domain.model;

import com.sbz.appa.commons.Checkpoint;
import com.sbz.appa.commons.ServiceType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class CarriageOrder extends ServiceOrder {
    private static final int PRICE_KM = 1070;

    public CarriageOrder(ServiceType type, Checkpoint originCheckpoint, Checkpoint destinationCheckpoint) {
        super(type, originCheckpoint, destinationCheckpoint);
    }

    @Override
    public Double getPrice() {
        if (distance == null)
            getShortestDistance();

        return distance == 0 ? 0.0 : Math.round((distance * PRICE_KM) * 100) / 100d;
    }
}
