package com.sbz.appa.core.domain.service;

import com.sbz.appa.commons.Checkpoint;
import com.sbz.appa.core.domain.model.Graph;
import com.sbz.appa.core.domain.model.Neighbor;
import com.sbz.appa.core.domain.model.Route;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@Getter
@NoArgsConstructor
public class ShortestPath {
    private Double distance;
    private Route route;


    // Dijkstra Algorithm
    public void findShortestPath(Graph graph, Checkpoint initialNode, Checkpoint finalNode) {
        log.info("Finding shortest path between {} and {}", initialNode, finalNode);
        Map<Checkpoint, Double> shortestPathDistances = new HashMap<>();
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
        distance = shortestPathDistances.get(finalNode);
        buildPath(previousNodes, initialNode, finalNode);
    }

    private void buildPath(
            Map<Checkpoint, Checkpoint> previousNodes,
            Checkpoint initialNode,
            Checkpoint currentNode) {
        Deque<Checkpoint> path = new ArrayDeque<>();
        while (previousNodes.get(currentNode) != null) {
            path.addFirst(currentNode);
            currentNode = previousNodes.get(currentNode);
        }
        path.addFirst(initialNode);
        route = Route.builder()
                .optimalRoute(path)
                .build();
    }

}












































