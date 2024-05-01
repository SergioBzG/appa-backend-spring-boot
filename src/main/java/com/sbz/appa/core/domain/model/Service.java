package com.sbz.appa.core.domain.model;

import com.sbz.appa.commons.Checkpoint;
import com.sbz.appa.commons.Nation;
import com.sbz.appa.commons.ServiceType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Service {
    private ServiceType type;
    private Nation originNation;
    private Nation destinationNation;
    private Checkpoint originCheckpoint;
    private Checkpoint destinationCheckpoint;
    private Route route;

    public abstract Double getPrice(double distance);
    public String getRoute() {
        return null;
    }
}
