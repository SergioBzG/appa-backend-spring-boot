package com.sbz.appa.core.domain.model;

import com.sbz.appa.commons.Checkpoint;
import com.sbz.appa.commons.ServiceType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class PackageOrder extends ServiceOrder {
    private static final int BASE_PRICE_SHORT_DISTANCE = 20000;
    private static final int BASE_PRICE_LONG_DISTANCE = 50000;
    private static final int BASE_PRICE_SMALL_VOLUME = 7000;
    private static final int BASE_PRICE_BIG_VOLUME = 10000;
    private static final int SHORT_DISTANCE = 80;
    private int length;
    private int width;
    private int height;
    private int weight;

    public PackageOrder(ServiceType type, Checkpoint originCheckpoint, Checkpoint destinationCheckpoint, int length, int width, int height, int weight) {
        super(type, originCheckpoint, destinationCheckpoint);
        this.length = length;
        this.width = width;
        this.height = height;
        this.weight = weight;
    }

    @Override
    public Double getPrice() {
        if (distance == null)
            getOptimalRouteAndDistance();

        int dimensionPrice = getVolume() > width ? BASE_PRICE_BIG_VOLUME : BASE_PRICE_SMALL_VOLUME;
        return distance >= SHORT_DISTANCE ?
                Math.round((BASE_PRICE_LONG_DISTANCE + distance * dimensionPrice) * 100) / 100d :
                distance == 0 ? 0.0 :
                ((BASE_PRICE_SHORT_DISTANCE + dimensionPrice) * 100) / 100d;
    }

    private int getVolume() {
        return height * width * length;
    }
}
