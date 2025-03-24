package com.maveric.seaexplorer.service;

import com.maveric.seaexplorer.controller.error.ProbeException;
import com.maveric.seaexplorer.controller.req.Coordinate;
import com.maveric.seaexplorer.controller.req.Direction;
import com.maveric.seaexplorer.controller.req.ProbeLocation;
import com.maveric.seaexplorer.controller.req.ProbeNextCoordinate;
import com.maveric.seaexplorer.repo.SeaObstacleRepo;
import com.maveric.seaexplorer.util.ApplicationHelper;
import com.maveric.seaexplorer.util.MessageSourceUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.maveric.seaexplorer.controller.req.ProbeLocation.HeadDirection;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SeaServiceTest {

    @Mock
    private MessageSourceUtil messageSource;

    @Mock
    private SeaCacheService seaCacheService;

    @Mock
    private SeaObstacleRepo seaObstacleRepo;

    @InjectMocks
    private ApplicationHelper helper = spy(ApplicationHelper.class);

    @InjectMocks
    private SeaService seaService = new SeaServiceImpl();

    @Test
    public void testFindCoordinate(){
        when(seaCacheService.getSeaCoordinates()).thenReturn(new Coordinate(5, 5));
        seaService.setProbeCoordinate(getProbeLocation(5, 4, HeadDirection.NORTH));
        when(messageSource.getMessage(anyString())).thenReturn("test");

        ProbeLocation probeLocation = seaService.findCoordinate(getProbeNextCoordinate(Direction.FRONT));
        assertEquals(probeLocation.getCoordinate().getX(), 5);
        assertEquals(probeLocation.getCoordinate().getY(), 5);
        assertEquals(probeLocation.getHeadDirection(), HeadDirection.NORTH);

        seaService.setProbeCoordinate(probeLocation);
        assertThrowsExactly(ProbeException.class, () -> {
            seaService.findCoordinate(getProbeNextCoordinate(Direction.FRONT));
        }, "test");

        when(seaCacheService.getSeaCoordinates()).thenReturn(new Coordinate(10, 10));
        seaService.setProbeCoordinate(getProbeLocation(5, 4, HeadDirection.NORTH));

        probeLocation = seaService.findCoordinate(getProbeNextCoordinate(Direction.RIGHT));
        assertEquals(probeLocation.getCoordinate().getX(), 6);
        assertEquals(probeLocation.getCoordinate().getY(), 4);
        assertEquals(probeLocation.getHeadDirection(), HeadDirection.EAST);

        seaService.setProbeCoordinate(probeLocation);
        probeLocation = seaService.findCoordinate(getProbeNextCoordinate(Direction.RIGHT));
        assertEquals(probeLocation.getCoordinate().getX(), 6);
        assertEquals(probeLocation.getCoordinate().getY(), 3);
        assertEquals(probeLocation.getHeadDirection(), HeadDirection.SOUTH);

        seaService.setProbeCoordinate(probeLocation);
        probeLocation = seaService.findCoordinate(getProbeNextCoordinate(Direction.RIGHT));
        assertEquals(probeLocation.getCoordinate().getX(), 5);
        assertEquals(probeLocation.getCoordinate().getY(), 3);
        assertEquals(probeLocation.getHeadDirection(), HeadDirection.WEST);

        seaService.setProbeCoordinate(getProbeLocation(5, 4, HeadDirection.NORTH));

        probeLocation = seaService.findCoordinate(getProbeNextCoordinate(Direction.LEFT));
        assertEquals(probeLocation.getCoordinate().getX(), 4);
        assertEquals(probeLocation.getCoordinate().getY(), 4);
        assertEquals(probeLocation.getHeadDirection(), HeadDirection.WEST);

        seaService.setProbeCoordinate(probeLocation);
        probeLocation = seaService.findCoordinate(getProbeNextCoordinate(Direction.LEFT));
        assertEquals(probeLocation.getCoordinate().getX(), 4);
        assertEquals(probeLocation.getCoordinate().getY(), 3);
        assertEquals(probeLocation.getHeadDirection(), HeadDirection.SOUTH);

        seaService.setProbeCoordinate(probeLocation);
        probeLocation = seaService.findCoordinate(getProbeNextCoordinate(Direction.LEFT));
        assertEquals(probeLocation.getCoordinate().getX(), 5);
        assertEquals(probeLocation.getCoordinate().getY(), 3);
        assertEquals(probeLocation.getHeadDirection(), HeadDirection.EAST);
    }

    private ProbeLocation getProbeLocation(int x, int y, HeadDirection direction){
        return ProbeLocation.builder()
                .coordinate(new Coordinate(x, y))
                .headDirection(direction).build();
    }

    private ProbeNextCoordinate getProbeNextCoordinate(Direction direction){
        return getProbeNextCoordinate(direction, false);
    }

    private ProbeNextCoordinate getProbeNextCoordinate(Direction direction, boolean ignoreObstacle){
        return ProbeNextCoordinate.builder()
                .direction(direction)
                .ignoreObstacle(ignoreObstacle)
                .build();
    }
}
