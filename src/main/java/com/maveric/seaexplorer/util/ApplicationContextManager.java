package com.maveric.seaexplorer.util;

public class ApplicationContextManager {

    private static final ThreadLocal<ApplicationContext> CONTEXT = new ThreadLocal<>();

    public static ApplicationContext getContext() {
        return CONTEXT.get();
    }

    public static void setContext(ApplicationContext context) {
        CONTEXT.set(context);
    }
}
