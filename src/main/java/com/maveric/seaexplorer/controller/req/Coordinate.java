package com.maveric.seaexplorer.controller.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Coordinate {
    @NotNull
    @Min(1)
    private int x;
    @NotNull
    @Min(1)
    private int y;
}
