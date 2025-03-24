package com.maveric.seaexplorer.service;

import com.maveric.seaexplorer.controller.req.Coordinate;
import com.maveric.seaexplorer.controller.req.NewObstacle;
import com.maveric.seaexplorer.controller.req.ProbeLocation;
import com.maveric.seaexplorer.controller.req.ProbeNextCoordinate;
import com.maveric.seaexplorer.controller.res.Obstacle;

import java.util.List;

public interface SeaService {

    boolean hasObstacle(Coordinate coordinate);

    ProbeLocation findCoordinate(ProbeNextCoordinate nextCoordinate);

    void setProbeCoordinate(ProbeLocation probeLocation);

    ProbeLocation getProbeCoordinate();

    List<Obstacle> getSeaObstacles();

    void createNewObstacle(NewObstacle obstacle);
}
