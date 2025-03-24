package com.maveric.seaexplorer.controller.res;

import com.maveric.seaexplorer.controller.req.ProbeLocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProbeMove {
    private ProbeLocation.HeadDirection headDirection;
    private boolean ignoreObstacle;
    private int coordinateX;
    private int coordinateY;
    private String updatedBy;
    private Date updatedAt;
}
