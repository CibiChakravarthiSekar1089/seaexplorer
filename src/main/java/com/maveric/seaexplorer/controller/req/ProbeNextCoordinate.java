package com.maveric.seaexplorer.controller.req;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProbeNextCoordinate {
    @NotNull
    private Direction direction;
    private boolean ignoreObstacle = false;
}
