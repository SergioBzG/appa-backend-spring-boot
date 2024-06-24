package com.sbz.appa.core.domain.model;

import com.sbz.appa.commons.Checkpoint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Deque;

@Data
@AllArgsConstructor
@Builder
public class DistancePathPair {
    private Double distance;
    private Deque<Checkpoint> path;
}
