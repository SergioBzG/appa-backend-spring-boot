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
    protected Route route;
    protected Double distance;

    public ServiceOrder(ServiceType type, Checkpoint originCheckpoint, Checkpoint destinationCheckpoint) {
        this.type = type;
        this.originCheckpoint = originCheckpoint;
        this.destinationCheckpoint = destinationCheckpoint;
    }

    public abstract Double getPrice();

    public void getOptimalRouteAndDistance() {
        log.info("Getting optimal route and distance");
        ShortestPath shortestPath = new ShortestPath();
        shortestPath.findShortestPath(Graph.getInstance(), originCheckpoint, destinationCheckpoint);
        distance = shortestPath.getDistance();
        route = shortestPath.getRoute();
        log.info("Optimal route gotten : {}", route.getOptimalRoute());
        log.info("Distance gotten: {}", distance);
    }

    public List<String> getRouteList() {
        if (route == null)
            getOptimalRouteAndDistance();

        return route.getOptimalRoute().stream()
                .map(Enum::name)
                .toList();
    }

    public static List<String> getRouteList(Checkpoint originCheckpoint, Checkpoint destinationCheckpoint) {
        ShortestPath shortestPath = new ShortestPath();
        shortestPath.findShortestPath(Graph.getInstance(), originCheckpoint, destinationCheckpoint);
        return shortestPath.getRoute().getOptimalRoute().stream()
                .map(Enum::name)
                .toList();
    }
}
