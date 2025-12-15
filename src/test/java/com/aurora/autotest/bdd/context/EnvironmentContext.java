package com.aurora.autotest.bdd.context;

public class EnvironmentContext {
    private static final String DEFAULT_GW_URL = "http://localhost:8083";

    private static final String CONSUL_URL = "http://localhost:8500";


    public static String gatewayBaseUrl() {
        return System.getProperty("api.base.url", DEFAULT_GW_URL);
    }

    public static String consulBaseUrl() {
        return System.getProperty("consul.base.url", CONSUL_URL);
    }
}
