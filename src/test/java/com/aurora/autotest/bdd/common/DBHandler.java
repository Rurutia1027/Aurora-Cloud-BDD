package com.aurora.autotest.bdd.common;

import com.aurora.autotest.bdd.context.ApiContext;
import com.aurora.autotest.bdd.context.EnvironmentContext;

import javax.naming.event.EventContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class DBHandler {
    // Map to hold DBHelper instance per service
    private static final Map<String, DBHandler> instances = new HashMap<>();

    private final Connection connection;

    private DBHandler(String url, String user, String password) {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to DB: " + e.getMessage(), e);
        }
    }

    /**
     * Get DBHandler for a specific service
     */
    public static DBHandler getInstance(String svcName) {
        if (!instances.containsKey(svcName)) {
            String url = EnvironmentContext.dbUrl(svcName);
            String user = EnvironmentContext.dbUser();
            String password = EnvironmentContext.dbPassword();

            if (url == null || url.isEmpty()) {
                throw new RuntimeException("Database URL not set for service: " + svcName);
            }

            instances.put(svcName, new DBHandler(url, user, password));
        }
        return instances.get(svcName);
    }

    /**
     * Query for a single value
     */
    public boolean recordExists(String query, Object... params) {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            throw new RuntimeException("Query failed: " + e.getMessage(), e);
        }
    }

    /**
     * Update/Delete operation
     */
    public int executeUpdate(String query, Object... params) {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            return stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Update/Delete failed: " + e.getMessage(), e);
        }
    }

    /**
     * Close connection
     */
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) connection.close();
        } catch (Exception ignored) {
        }
    }
}
