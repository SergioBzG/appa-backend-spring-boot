package com.sbz.appa.core.domain.model;

import com.sbz.appa.commons.Checkpoint;
import com.sbz.appa.commons.ServiceType;
import com.sbz.appa.core.domain.service.ShortestPath;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Data
@Slf4j
public abstract class ServiceOrder {
    protected ServiceType type;
    protected Checkpoint originCheckpoint;
    protected Checkpoint destinationCheckpoint;
    protected Double distance;

    public ServiceOrder(ServiceType type, Checkpoint originCheckpoint, Checkpoint destinationCheckpoint) {
        this.type = type;
        this.originCheckpoint = originCheckpoint;
        this.destinationCheckpoint = destinationCheckpoint;
    }

    public abstract Double getPrice();

    public void getShortestDistance() {
        log.info("Getting shortest distance");

        DistancePathPair distancePathPair = ShortestPath.findShortestPath(
                Graph.getInstance(), originCheckpoint, destinationCheckpoint, false);
        distance = distancePathPair.getDistance();

        log.info("Distance gotten: {}", distance);
    }

    public static List<String> getPathList(Checkpoint originCheckpoint, Checkpoint destinationCheckpoint) {
        log.info("Getting optimal route");
        DistancePathPair distancePathPair = ShortestPath.findShortestPath(
                Graph.getInstance(), originCheckpoint, destinationCheckpoint, true);
        return distancePathPair.getPath().stream()
                .map(Enum::name)
                .toList();
    }
}
