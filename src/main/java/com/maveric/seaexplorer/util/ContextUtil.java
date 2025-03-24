package com.maveric.seaexplorer.util;

import java.util.Optional;

public class ContextUtil {

    private static final String DEFAULT_USERNAME = "ADMIN";
    private static final ApplicationContext DEFAULT_CONTEXT = ApplicationContext.builder().build();

    public static String getUserName(){
        return DEFAULT_USERNAME;
        /*return Optional.ofNullable(Optional.ofNullable(ApplicationContextManager.getContext())
                .orElse(DEFAULT_CONTEXT).getUser())
                .map(u -> u == null ? DEFAULT_USERNAME : u.getName()).get();*/
    }
}
