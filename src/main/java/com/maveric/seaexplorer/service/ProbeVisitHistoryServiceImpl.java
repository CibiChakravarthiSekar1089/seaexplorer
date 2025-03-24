package com.maveric.seaexplorer.service;

import com.maveric.seaexplorer.controller.error.ProbeException;
import com.maveric.seaexplorer.controller.req.ProbeLocation;
import com.maveric.seaexplorer.controller.req.ProbeNextCoordinate;
import com.maveric.seaexplorer.controller.res.ProbeMove;
import com.maveric.seaexplorer.dto.ProbeVisitHistory;
import com.maveric.seaexplorer.repo.ProbeVisitHistoryRepo;
import com.maveric.seaexplorer.util.ApplicationHelper;
import com.maveric.seaexplorer.util.ContextUtil;
import com.maveric.seaexplorer.util.MessageSourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProbeVisitHistoryServiceImpl implements ProbeVisitHistoryService {

    @Autowired
    private MessageSourceUtil messageSource;

    @Autowired
    private ProbeVisitHistoryRepo visitRepo;

    @Autowired
    private SeaService seaService;

    @Autowired
    private ApplicationHelper helper;

    @Override
    @Transactional
    public void moveProbeNextCoordinate(ProbeNextCoordinate nextCoordinate){
        ProbeLocation nextProbeLocation = seaService.findCoordinate(nextCoordinate);
        if(!nextCoordinate.isIgnoreObstacle() && seaService.hasObstacle(nextProbeLocation.getCoordinate())){
            throw new ProbeException(messageSource.getMessage(MessageSourceUtil.ERR_PROBE_OBSTACLE));
        }

        visitRepo.save(ProbeVisitHistory.builder()
                .updatedAt(new Date())
                .updatedBy(ContextUtil.getUserName())
                .ignoreObstacle(nextCoordinate.isIgnoreObstacle())
                .headDirection(nextProbeLocation.getHeadDirection())
                .coordinateX(nextProbeLocation.getCoordinate().getX())
                .coordinateY(nextProbeLocation.getCoordinate().getY())
                .build());

        seaService.setProbeCoordinate(nextProbeLocation);
    }

    @Override
    public List<ProbeMove> listProbeVisitHistory(int page, int pageSize) {
        return visitRepo.findAll(PageRequest.of(page, pageSize, Sort.by("updatedAt").descending()))
                .stream()
                .map(this::transaformProbeMove)
                .collect(Collectors.toList());
    }

    private ProbeMove transaformProbeMove(ProbeVisitHistory visitHistory){
        return ProbeMove.builder()
                .ignoreObstacle(visitHistory.isIgnoreObstacle())
                .headDirection(visitHistory.getHeadDirection())
                .coordinateX(visitHistory.getCoordinateX())
                .coordinateY(visitHistory.getCoordinateY())
                .updatedAt(visitHistory.getUpdatedAt())
                .updatedBy(visitHistory.getUpdatedBy())
                .build();
    }

    @Override
    @Transactional
    public void resetProbeLocation(ProbeLocation newLocation){
        helper.checkInvalidCoordinates(newLocation.getCoordinate(), MessageSourceUtil.ERR_SEA_OBSTACLE_INVALID_COORDINATES);

        visitRepo.save(ProbeVisitHistory.builder()
                .updatedAt(new Date())
                .updatedBy(ContextUtil.getUserName())
                .ignoreObstacle(false)
                .headDirection(newLocation.getHeadDirection())
                .coordinateX(newLocation.getCoordinate().getX())
                .coordinateY(newLocation.getCoordinate().getY())
                .build());

        seaService.setProbeCoordinate(newLocation);
    }
}

