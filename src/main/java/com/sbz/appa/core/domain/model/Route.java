package com.sbz.appa.core.domain.model;

import com.sbz.appa.commons.Checkpoint;
import lombok.*;

import java.util.Deque;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Route {
    private Deque<Checkpoint> optimalRoute;
}
