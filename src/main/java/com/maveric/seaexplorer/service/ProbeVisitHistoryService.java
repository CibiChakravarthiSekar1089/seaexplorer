package com.maveric.seaexplorer.service;

import com.maveric.seaexplorer.controller.req.ProbeLocation;
import com.maveric.seaexplorer.controller.req.ProbeNextCoordinate;
import com.maveric.seaexplorer.controller.res.ProbeMove;

import java.util.List;

public interface ProbeVisitHistoryService {

    List<ProbeMove> listProbeVisitHistory(int page, int pageSize);

    void resetProbeLocation(ProbeLocation newLocation);

    void moveProbeNextCoordinate(ProbeNextCoordinate nextCoordinate);
}
