package com.sbz.appa.core.domain.model;

import com.sbz.appa.commons.Checkpoint;
import lombok.*;

import java.util.Deque;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Route {
    private Deque<Checkpoint> optimalRoute;

    @Override
    public String toString() {
        return "Route{" +
                "optimalRoute=" + optimalRoute +
                '}';
    }
}
