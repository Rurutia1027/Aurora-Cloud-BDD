package com.aurora.autotest.bdd.context;

public class EnvironmentContext {
    private static final String DEFAULT_GW_URL = "http://localhost:8083";

    public static String gatewayBaseUrl() {
        return System.getProperty("api.base.url", DEFAULT_GW_URL);
    }
}
