package com.watchlist.services;

import com.watchlist.models.User;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class UserService {
    private final String JDBC_URL = "jdbc:mysql://localhost:3306/moviedb";
    private final String DB_USER = "root";
    private final String DB_PASS = "your_password";

    public boolean registerUser(User user) {
        String sql = "INSERT INTO User (username, email, password_hash) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPasswordHash());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean authenticateUser(String username, String password) {
        String sql = "SELECT password_hash FROM User WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String hashed = rs.getString("password_hash");
                return password.equals(hashed); // Replace with BCrypt later
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
