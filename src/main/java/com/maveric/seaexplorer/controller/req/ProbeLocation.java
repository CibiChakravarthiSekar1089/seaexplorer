package com.maveric.seaexplorer.controller.req;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProbeLocation {

    public enum HeadDirection {
        NORTH,
        SOUTH,
        WEST,
        EAST
    }

    @NotNull
    private Coordinate coordinate;

    @NotNull
    private HeadDirection headDirection;
}
