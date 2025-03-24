package com.maveric.seaexplorer.service;

import com.maveric.seaexplorer.controller.error.ProbeException;
import com.maveric.seaexplorer.controller.req.*;
import com.maveric.seaexplorer.controller.res.Obstacle;
import com.maveric.seaexplorer.dto.SeaObstacle;
import com.maveric.seaexplorer.repo.SeaObstacleRepo;
import com.maveric.seaexplorer.util.ApplicationHelper;
import com.maveric.seaexplorer.util.ContextUtil;
import com.maveric.seaexplorer.util.MessageSourceUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.maveric.seaexplorer.controller.req.ProbeLocation.HeadDirection;

@Service
public class SeaServiceImpl implements SeaService {

    @Autowired
    private MessageSourceUtil messageSource;

    @Autowired
    private SeaCacheService seaCacheService;

    @Autowired
    private SeaObstacleRepo seaObstacleRepo;

    @Autowired
    private ApplicationHelper helper;

    private AtomicReference<ProbeLocation> currentProbeLocation = new AtomicReference<>();

    @PostConstruct
    public void intialize() {
        currentProbeLocation.set(ProbeLocation.builder()
                .coordinate(new Coordinate(1, 1))
                .headDirection(HeadDirection.NORTH)
                .build());
    }

    @Override
    public boolean hasObstacle(Coordinate coordinate){
        var id = seaCacheService.getIdByCoordinates(coordinate);
        return seaCacheService.getSeaObstacles().contains(id);
    }

    @Override
    public ProbeLocation findCoordinate(ProbeNextCoordinate nextCoordinate){
        ProbeLocation newProbeLocation = null;
        var probeLocation = currentProbeLocation.get();

        if(probeLocation.getHeadDirection() == HeadDirection.NORTH) {
            newProbeLocation = manipulateCoordinate(nextCoordinate.getDirection(), probeLocation, false, true, false);
        } else if(probeLocation.getHeadDirection() == HeadDirection.SOUTH){
            newProbeLocation = manipulateCoordinate(nextCoordinate.getDirection(), probeLocation, false, false, true);
        } else if(probeLocation.getHeadDirection() == HeadDirection.WEST){
            newProbeLocation = manipulateCoordinate(nextCoordinate.getDirection(), probeLocation, true, false, false);
        } else if(probeLocation.getHeadDirection() == HeadDirection.EAST){
            newProbeLocation = manipulateCoordinate(nextCoordinate.getDirection(), probeLocation, true, true, true);
        }

        helper.checkInvalidCoordinates(newProbeLocation.getCoordinate(), MessageSourceUtil.ERR_PROBE_ENDOFCOORDINATE);

        return newProbeLocation;
    }

    public ProbeLocation manipulateCoordinate(
            Direction nxtDir,
            ProbeLocation probeLocation,
            boolean fbDirectionX,
            boolean frontActionAdd,
            boolean leftActionAdd
    ){
        var probeCoordinate = probeLocation.getCoordinate();
        int x = probeCoordinate.getX(), y = probeCoordinate.getY();
        int front = frontActionAdd ? 1 : -1;
        int left = leftActionAdd ? 1 : -1;
        int back = front * -1;
        int right = left * -1;

        //current probe direction x=5, y=3, direction=NORTH,
        //fbDirectionX=false frontActionAdd=true leftActionAdd=false
        //the probe is facing either east or west
        if(fbDirectionX){
            switch (nxtDir) {
                case FRONT:
                    x += front; break;
                case BACK:
                    x += back; break;
                case LEFT:
                    y += left; break;
                case RIGHT:
                    y += right; break;
            }
            //the probe is facing either north or south
        } else {
            switch (nxtDir) {
                case FRONT:
                    y += front; break;
                case BACK:
                    y += back; break;
                case LEFT:
                    x += left; break;
                case RIGHT:
                    x += right; break;
            }
        }

        return ProbeLocation.builder()
                .coordinate(new Coordinate(x, y))
                .headDirection(findHeadDirection(nxtDir, probeLocation.getHeadDirection()))
                .build();
    }

    public HeadDirection findHeadDirection(Direction nxtDir, HeadDirection currDirection){
        if(nxtDir == Direction.LEFT){
            return switch (currDirection) {
                case NORTH -> HeadDirection.WEST;
                case EAST -> HeadDirection.NORTH;
                case SOUTH -> HeadDirection.EAST;
                case WEST -> HeadDirection.SOUTH;
            };
        } else if(nxtDir == Direction.RIGHT){
            return switch (currDirection) {
                case NORTH -> HeadDirection.EAST;
                case EAST -> HeadDirection.SOUTH;
                case SOUTH -> HeadDirection.WEST;
                case WEST -> HeadDirection.NORTH;
            };
        }
        return currDirection;
    }

    @Override
    public void setProbeCoordinate(ProbeLocation probeLocation){
        currentProbeLocation.set(probeLocation);
    }

    @Override
    public ProbeLocation getProbeCoordinate() {
        ProbeLocation location = currentProbeLocation.get();
        return ProbeLocation.builder()
                .headDirection(location.getHeadDirection())
                .coordinate(new Coordinate(location.getCoordinate().getX(), location.getCoordinate().getY()))
                .build();
    }

    @Override
    public List<Obstacle> getSeaObstacles(){
        return Optional.ofNullable(seaObstacleRepo.findAll())
                .orElse(new ArrayList<>())
                .stream()
                .map(this::transaformObstacle)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createNewObstacle(NewObstacle obstacle){
        Coordinate seaCoordinates = seaCacheService.getSeaCoordinates();
        if(obstacle.getX() > seaCoordinates.getX()
                || obstacle.getY() > seaCoordinates.getY()){
            throw new ProbeException(messageSource.getMessage(MessageSourceUtil.ERR_SEA_OBSTACLE_INVALID_COORDINATES));
        }

        seaObstacleRepo.save(SeaObstacle.builder()
                .x(obstacle.getX())
                .y(obstacle.getY())
                .updatedAt(new Date())
                .updatedBy(ContextUtil.getUserName())
                .build());

        seaCacheService.updateObstacles(new Coordinate(obstacle.getX(), obstacle.getY()));
    }

    private Obstacle transaformObstacle(SeaObstacle seaObstacle){
        return Obstacle.builder()
                .x(seaObstacle.getX())
                .y(seaObstacle.getY())
                .updatedBy(seaObstacle.getUpdatedBy())
                .updatedAt(seaObstacle.getUpdatedAt())
                .build();
    }
}

