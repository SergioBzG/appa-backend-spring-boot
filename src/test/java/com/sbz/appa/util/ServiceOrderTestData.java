package com.sbz.appa.util;

import com.sbz.appa.commons.Checkpoint;
import com.sbz.appa.commons.ServiceType;
import com.sbz.appa.core.domain.model.CarriageOrder;
import com.sbz.appa.core.domain.model.ServiceOrder;

public class ServiceOrderTestData {

    public static ServiceOrder createTestServiceOrderCarriage() {
        return new CarriageOrder(
                ServiceType.CARRIAGE,
                Checkpoint.NORTHERN_WATER,
                Checkpoint.SOUTHERN_AIR
        );
    }
}
