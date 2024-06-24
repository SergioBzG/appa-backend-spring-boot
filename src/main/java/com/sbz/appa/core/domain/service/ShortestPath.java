package com.sbz.appa.core.domain.service;

import com.sbz.appa.commons.Checkpoint;
import com.sbz.appa.core.domain.model.DistancePathPair;
import com.sbz.appa.core.domain.model.Graph;
import com.sbz.appa.core.domain.model.Neighbor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Getter
@NoArgsConstructor
@Component
public class ShortestPath {

    // Dijkstra Algorithm
    public static DistancePathPair findShortestPath(Graph graph, Checkpoint initialNode, Checkpoint finalNode, boolean getPath) {
        log.info("Finding shortest path between {} and {}", initialNode, finalNode);
        Map<Checkpoint, Double> shortestPathDistances = new HashMap<>();

        // For backtracking
        Map<Checkpoint, Checkpoint> previousNodes = new HashMap<>();

        for(Checkpoint checkpoint : graph.getMap().keySet()) {
            shortestPathDistances.put(checkpoint, Double.MAX_VALUE);
            previousNodes.put(checkpoint, null);
        }
        shortestPathDistances.put(initialNode, 0d);
        PriorityQueue<Neighbor> distances = new PriorityQueue<>();
        distances.add(new Neighbor(initialNode, 0d));

        while (!distances.isEmpty()) {
            Neighbor vertex = distances.poll();
            Checkpoint currentNode = vertex.getNeighborCheckpoint();
            Double currentDistance = vertex.getDistance();

            if (currentDistance > shortestPathDistances.get(currentNode))
                continue;

            for (Neighbor neighbor : graph.getMap().get(currentNode)) {
                Double distanceToNeighbor = currentDistance + neighbor.getDistance();

                if (distanceToNeighbor < shortestPathDistances.get(neighbor.getNeighborCheckpoint())) {
                    shortestPathDistances.put(neighbor.getNeighborCheckpoint(), distanceToNeighbor);
                    distances.add(new Neighbor(neighbor.getNeighborCheckpoint(), distanceToNeighbor));
                    previousNodes.put(neighbor.getNeighborCheckpoint(), currentNode);
                }
            }
        }
        Double distance = shortestPathDistances.get(finalNode);
        // If only distance is required
        if (!getPath)
            return DistancePathPair.builder()
                    .distance(distance)
                    .build();
        // If path is required
        Deque<Checkpoint> path = buildPath(previousNodes, initialNode, finalNode);
        if (path.isEmpty()) {
            log.error("Route not found between {} and {}", initialNode, finalNode);
            throw new RuntimeException("Route not found between " + initialNode + " and " + finalNode);
        }
        return DistancePathPair.builder()
                .distance(distance)
                .path(path)
                .build();
    }

    private static Deque<Checkpoint> buildPath(Map<Checkpoint, Checkpoint> previousNodes, Checkpoint initialNode, Checkpoint currentNode) {
        Deque<Checkpoint> path = new ArrayDeque<>();
        while (previousNodes.get(currentNode) != null) {
            path.addFirst(currentNode);
            currentNode = previousNodes.get(currentNode);
        }
        path.addFirst(initialNode);
        return path;
    }

}