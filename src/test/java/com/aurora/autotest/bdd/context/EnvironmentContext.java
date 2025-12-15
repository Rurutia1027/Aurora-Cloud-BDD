package com.aurora.autotest.bdd.context;

public class EnvironmentContext {
    private static final String DEFAULT_GW_URL = "http://localhost:8083";
    private static final String DEFAULT_CONSUL_URL = "http://localhost:8500";
    private static final String DEFAULT_DB_CUSTOMER_URL = "jdbc:postgresql://localhost:5432/customer";
    private static final String DEFAULT_DB_NOTIFICATION_URL = "jdbc:postgresql://localhost:5432/notification";
    private static final String DEFAULT_DB_FRAUD_URL = "jdbc:postgresql://localhost:5432/fraud";
    private static final String DEFAULT_DB_USER = "admin";
    private static final String DEFAULT_DB_PASSWORD = "admin";


    private static String getEnvOrDefault(String key, String defaultValue) {
        String sys = System.getProperty(key);
        if (sys != null && !sys.isEmpty()) return sys;

        // support e.g. db.customer.url -> DB_CUSTOMER_URL
        String envKey = key.toUpperCase().replace('.', '_');
        String env = System.getenv(envKey);
        if (env != null && !env.isEmpty()) return env;

        return defaultValue;
    }

    public static String gatewayBaseUrl() {
        return System.getProperty("api.base.url", DEFAULT_GW_URL);
    }

    public static String consulBaseUrl() {
        return System.getProperty("consul.base.url", DEFAULT_CONSUL_URL);
    }

    public static String dbUrl(String serviceName) {
        return getEnvOrDefault(
                "db." + serviceName + ".url",
                switch (serviceName) {
                    case "customer" -> DEFAULT_DB_CUSTOMER_URL;
                    case "notification" -> DEFAULT_DB_NOTIFICATION_URL;
                    case "fraud" -> DEFAULT_DB_FRAUD_URL;
                    default ->
                            throw new RuntimeException("No default DB URL for service: " + serviceName);
                }
        );
    }

    public static String dbUser() {
        return getEnvOrDefault("db.user", DEFAULT_DB_USER);
    }

    public static String dbPassword() {
        return getEnvOrDefault("db.password", DEFAULT_DB_PASSWORD);
    }
}
