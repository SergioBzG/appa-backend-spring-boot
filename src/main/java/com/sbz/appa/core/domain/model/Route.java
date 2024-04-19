package com.sbz.appa.core.domain.model;

import com.sbz.appa.commons.Checkpoint;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Route {
    private List<Checkpoint> optimalRoute;
}
