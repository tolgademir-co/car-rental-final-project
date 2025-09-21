package com.tolgademir.dao;

import com.tolgademir.model.User;
import com.tolgademir.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object for User
 * // users tablosu ile ilgili veritabanı işlemleri
 */
public class UserDao {

    /**
     * Insert new user into DB
     * // Yeni kullanıcı ekler
     */
    public boolean insert(User user) {
        String sql = "INSERT INTO users (name, email, password_hash, role, account_type, age) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPasswordHash());
            stmt.setString(4, user.getRole());
            stmt.setString(5, user.getAccountType());
            stmt.setInt(6, user.getAge());

            stmt.executeUpdate();
            System.out.println("✅ User inserted: " + user.getEmail());
            return true;

        } catch (SQLException e) {
            throw new RuntimeException("❌ Failed to insert user: " + e.getMessage(), e);
        }
    }

    /**
     * Find user by email and passwordHash
     * // Email ve şifre ile kullanıcı bulur
     */
    public User findByEmailAndPassword(String email, String passwordHash) {
        String sql = "SELECT * FROM users WHERE email = ? AND password_hash = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, passwordHash);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        rs.getString("role"),
                        rs.getString("account_type"),
                        rs.getInt("age")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("❌ Failed to find user: " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * Find user by ID
     * // ID ile kullanıcı bulur
     */
    public User findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        rs.getString("role"),
                        rs.getString("account_type"),
                        rs.getInt("age")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("❌ Failed to find user by ID: " + e.getMessage(), e);
        }
        return null;
    }
}