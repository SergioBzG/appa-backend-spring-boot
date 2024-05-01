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
        return distance == 0 ? 0.0 : Math.round((distance * PRICE_KM) * 100) / 100d;
    }
}
