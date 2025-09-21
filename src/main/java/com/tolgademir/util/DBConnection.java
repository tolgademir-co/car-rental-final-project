package com.tolgademir.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection -> Manages PostgreSQL database connection
 * // PostgreSQL veritabanı bağlantısını yönetir
 */
public class DBConnection {

    // --- DB bağlantı bilgileri ---
    private static final String URL = "jdbc:postgresql://localhost:5432/rentacar";
    private static final String USER = "postgres";
    private static final String PASSWORD = "0102030123Td";

    /**
     * Get a new DB connection
     * // Yeni bir veritabanı bağlantısı döndürür
     */
    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver"); // PostgreSQL driver yükleniyor
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("❌ PostgreSQL JDBC Driver not found. Did you add dependency?", e);
        } catch (SQLException e) {
            throw new RuntimeException("❌ Cannot connect to database: " + e.getMessage(), e);
        }
    }
}