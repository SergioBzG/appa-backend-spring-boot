package com.sbz.appa.core.domain.model;

import com.sbz.appa.commons.Checkpoint;

import java.util.List;
import java.util.Map;


public class Graph {
    private final Map<Checkpoint, List<Neighbor>> map;
    private static Graph graph;

    private Graph() {
        map = buildMap();
    }

    private Map<Checkpoint, List<Neighbor>> buildMap() {
        Map<Checkpoint, List<Neighbor>> map = new java.util.HashMap<>(Map.of(
                Checkpoint.FIRE_CAPITAL, List.of(
                        new Neighbor(Checkpoint.WESTERN_AIR, 213.6937),
                        new Neighbor(Checkpoint.SHU_JING, 266.5276),
                        new Neighbor(Checkpoint.SOUTHERN_AIR, 332.0256)
                ),
                Checkpoint.WESTERN_AIR, List.of(
                        new Neighbor(Checkpoint.FIRE_CAPITAL, 213.6937),
                        new Neighbor(Checkpoint.SHU_JING, 165.7588),
                        new Neighbor(Checkpoint.ABBEY, 145.3444)
                ),
                Checkpoint.ABBEY, List.of(
                        new Neighbor(Checkpoint.NORTHERN_AIR, 195d),
                        new Neighbor(Checkpoint.SHU_JING, 102.1812),
                        new Neighbor(Checkpoint.GAIPAN_VILLAGE, 126.5898),
                        new Neighbor(Checkpoint.NORTHERN_WATER, 172.3078)
                ),
                Checkpoint.NORTHERN_WATER, List.of(
                        new Neighbor(Checkpoint.ABBEY, 172.3078),
                        new Neighbor(Checkpoint.NORTHERN_AIR, 99.6443)
                ),
                Checkpoint.SHU_JING, List.of(
                        new Neighbor(Checkpoint.FIRE_CAPITAL, 266.5276),
                        new Neighbor(Checkpoint.WESTERN_AIR, 165.7588),
                        new Neighbor(Checkpoint.ABBEY, 102.1812),
                        new Neighbor(Checkpoint.GAIPAN_VILLAGE, 139.1761),
                        new Neighbor(Checkpoint.SI_WONG, 276.5248),
                        new Neighbor(Checkpoint.SOUTHERN_AIR, 234.6912)
                ),
                Checkpoint.NORTHERN_AIR, List.of(
                        new Neighbor(Checkpoint.NORTHERN_WATER, 99.6443),
                        new Neighbor(Checkpoint.ABBEY, 195d),
                        new Neighbor(Checkpoint.BA_SING_SE, 191.0104),
                        new Neighbor(Checkpoint.GAIPAN_VILLAGE, 167.6305)
                ),
                Checkpoint.GAIPAN_VILLAGE, List.of(
                        new Neighbor(Checkpoint.NORTHERN_AIR, 167.6305),
                        new Neighbor(Checkpoint.ABBEY, 126.5898),
                        new Neighbor(Checkpoint.BA_SING_SE, 214.7673),
                        new Neighbor(Checkpoint.SI_WONG, 440.2544),
                        new Neighbor(Checkpoint.SHU_JING, 139.1761)
                ),
                Checkpoint.BA_SING_SE, List.of(
                        new Neighbor(Checkpoint.NORTHERN_AIR, 191.0104),
                        new Neighbor(Checkpoint.GAIPAN_VILLAGE, 214.7673),
                        new Neighbor(Checkpoint.EASTERN_AIR, 236.3154)
                ),
                Checkpoint.SI_WONG, List.of(
                        new Neighbor(Checkpoint.GAIPAN_VILLAGE, 440.2544),
                        new Neighbor(Checkpoint.SHU_JING, 276.5248),
                        new Neighbor(Checkpoint.SOUTHERN_AIR, 291.6058)
                ),
                Checkpoint.SOUTHERN_AIR, List.of(
                        new Neighbor(Checkpoint.SI_WONG, 291.6058),
                        new Neighbor(Checkpoint.SHU_JING, 234.6912),
                        new Neighbor(Checkpoint.FIRE_CAPITAL, 332.0256),
                        new Neighbor(Checkpoint.SOUTHERN_WATER, 121.1981)
                )
        ));
        map.put(Checkpoint.SOUTHERN_WATER, List.of(
                        new Neighbor(Checkpoint.SOUTHERN_AIR,  121.1981)
                )
        );
        map.put(Checkpoint.EASTERN_AIR, List.of(
                        new Neighbor(Checkpoint.BA_SING_SE,  236.3154)
                )
        );
        return map;
    }

    public static Graph getInstance() {
        if (graph == null)
            graph = new Graph();

        return graph;
    }

    public Map<Checkpoint, List<Neighbor>> getMap() {
        return map;
    }

}
