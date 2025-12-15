package com.aurora.autotest.bdd.context;

import java.util.Map;

public class ApiContext {
    // API paths per service
    private static final Map<String, String> API_PATHS = Map.of(
            "customer.register", "/api/v1/customers",
            "customer.get", "/api/v1/customers/{id}",
            "notification.send", "/api/v1/notification",
            "fraud.check", "/api/v1/fraud/check/{id}"
    );

    public static String path(String key) {
        if (!API_PATHS.containsKey(key)) {
            throw new IllegalArgumentException("API path not found: " + key);
        }
        return API_PATHS.get(key);
    }
}
