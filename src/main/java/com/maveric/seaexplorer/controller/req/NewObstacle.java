package com.maveric.seaexplorer.controller.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewObstacle {
    @NotNull
    @Min(1)
    private Integer x;
    @NotNull
    @Min(1)
    private Integer y;
}
