package com.maveric.seaexplorer.util;

import com.maveric.seaexplorer.controller.error.ProbeException;
import com.maveric.seaexplorer.controller.req.Coordinate;
import com.maveric.seaexplorer.service.SeaCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationHelper {

    @Autowired
    private SeaCacheService seaCacheService;

    @Autowired
    private MessageSourceUtil messageSource;

    public void checkInvalidCoordinates(Coordinate newCoordinate, String errorMsg) {
        Coordinate seaCoordinate = seaCacheService.getSeaCoordinates();
        if(newCoordinate.getX() > seaCoordinate.getX()
                || newCoordinate.getY() > seaCoordinate.getY()){
            throw new ProbeException(messageSource.getMessage(errorMsg));
        }
    }

}
