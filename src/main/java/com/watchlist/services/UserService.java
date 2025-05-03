package com.watchlist.services;

import com.watchlist.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;


@Service
public class UserService {

    private final DataSource dataSource;

    @Autowired
    public UserService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean registerUser(User user) {
        String sql = "INSERT INTO User (username, email, password_hash) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
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
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String hashed = rs.getString("password_hash");
                return password.equals(hashed); // Replace with hashing later
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public Long getUserIdByUsername(String username) {
        String sql = "SELECT user_id FROM User WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, username);
    }
    
}
