package com.aurora.autotest.bdd.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBHelper {
    private static DBHelper instance;

    private Connection connection;

    private DBHelper(String url, String user, String password) {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to DB: " + e.getMessage(), e);
        }
    }
    /** Singleton accessor */
    public static DBHelper getInstance() {
        if (instance == null) {
            String url = System.getProperty("db.customer.url", "jdbc:postgresql://localhost:5432/customer");
            String user = System.getProperty("db.user", "admin");
            String password = System.getProperty("db.password", "admin");
            instance = new DBHelper(url, user, password);
        }
        return instance;
    }


    /** Query for a single value */
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

    /** Close connection */
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) connection.close();
        } catch (Exception ignored) {}
    }


    /** Delete / Update operation */
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
}
