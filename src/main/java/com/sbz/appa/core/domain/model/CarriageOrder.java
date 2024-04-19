package com.sbz.appa.core.domain.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarriageOrder extends Service {
    private static final int PRICE_KM = 1070;
    @Override
    public Double getPrice(double distance) {
        if(distance == 0)
            return 0.0;
        return Math.round((distance * PRICE_KM) * 100) / 100d;
    }
}
