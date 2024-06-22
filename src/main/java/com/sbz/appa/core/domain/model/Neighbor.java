package com.sbz.appa.core.domain.model;

import com.sbz.appa.commons.Checkpoint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
public class Neighbor implements Comparable<Neighbor> {
    private Checkpoint neighborCheckpoint;
    private Double distance;

    @Override
    public int compareTo(Neighbor neighbor) {
        return Double.compare(distance, neighbor.getDistance());
    }

    @Override
    public String toString() {
        return "Neighbor{" +
                "neighborCheckpoint=" + neighborCheckpoint +
                ", distance=" + distance +
                '}';
    }
}
