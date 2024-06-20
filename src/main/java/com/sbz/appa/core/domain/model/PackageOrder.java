package com.sbz.appa.core.domain.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PackageOrder extends Service {
    private static final int BASE_PRICE_SHORT_DISTANCE = 20000;
    private static final int BASE_PRICE_LONG_DISTANCE = 50000;
    private static final int BASE_PRICE_SMALL_VOLUME = 7000;
    private static final int BASE_PRICE_BIG_VOLUME = 10000;
    private static final int SHORT_DISTANCE = 80;
    private int length;
    private int width;
    private int height;
    private int weight;

    @Override
    public Double getPrice(double distance) {
        if(distance == 0)
            return 0.0;
        int dimensionPrice = getVolume() > width ? BASE_PRICE_BIG_VOLUME : BASE_PRICE_SMALL_VOLUME;
        return distance >= SHORT_DISTANCE ?
                Math.round((BASE_PRICE_LONG_DISTANCE + distance * dimensionPrice) * 100) / 100d :
                ((BASE_PRICE_SHORT_DISTANCE + dimensionPrice) * 100) / 100d;
    }

    private int getVolume() {
        return height * width * length;
    }
}
