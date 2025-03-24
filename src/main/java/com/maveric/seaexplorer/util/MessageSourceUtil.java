package com.maveric.seaexplorer.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageSourceUtil {

    public static final String ERR_PROBE_OBSTACLE = "error.probe.obstacle";
    public static final String ERR_PROBE_ENDOFCOORDINATE = "error.probe.endOfCoordinate";
    public static final String ERR_SEA_OBSTACLE_INVALID_COORDINATES = "error.sea.obstacles.invalid.coordinates";
    public static final String ERR_PROBE_RESET_INVALID_COORDINATE = "error.probe.reset.invalid.coordinate";


    @Autowired
    private MessageSource messageSource;

    public String getMessage(String key, Object... args){
        return messageSource.getMessage(key, args, Locale.ENGLISH);
    }

    public String getMessage(String key){
        return getMessage(key, null);
    }
}
