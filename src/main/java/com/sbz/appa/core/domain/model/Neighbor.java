package com.sbz.appa.core.domain.model;

import com.sbz.appa.commons.Checkpoint;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Neighbor implements Comparable<Neighbor> {
    private Checkpoint neighborCheckpoint;
    private Double distance;

    @Override
    public int compareTo(Neighbor neighbor) {
        return Double.compare(distance, neighbor.getDistance());
    }
}
