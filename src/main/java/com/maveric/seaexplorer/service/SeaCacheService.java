package com.maveric.seaexplorer.service;

import com.maveric.seaexplorer.controller.req.Coordinate;

import java.util.Set;

public interface SeaCacheService {

    Coordinate getSeaCoordinates();

    Set<String> loadObstacles();

    Set<String> getSeaObstacles();

    Set<String> updateObstacles(Coordinate coordinate);

    String getIdByCoordinates(Coordinate coordinate);
}
