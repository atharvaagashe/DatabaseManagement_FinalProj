package com.watchlist.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.watchlist.models.Friendship;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FriendshipRepositoryImpl implements FriendshipRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean addFriendship(int userId, int friendId) {
        try {
            // The database has a CHECK (user_id1 < user_id2) constraint
            // Make sure we're respecting that constraint
            int user1 = Math.min(userId, friendId);
            int user2 = Math.max(userId, friendId);
            
            String sql = "INSERT INTO Friendship (user_id1, user_id2, since_date) VALUES (?, ?, CURDATE())";
            int rowsAffected = jdbcTemplate.update(sql, user1, user2);
            return rowsAffected > 0;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeFriendship(int userId, int friendId) {
        try {
            // The database has a CHECK (user_id1 < user_id2) constraint
            // Make sure we're using the correct order for the query
            int user1 = Math.min(userId, friendId);
            int user2 = Math.max(userId, friendId);
            
            String sql = "DELETE FROM Friendship WHERE user_id1 = ? AND user_id2 = ?";
            int rowsAffected = jdbcTemplate.update(sql, user1, user2);
            return rowsAffected > 0;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isFriendship(int userId, int friendId) {
        try {
            // The database has a CHECK (user_id1 < user_id2) constraint
            // Make sure we're using the correct order for the query
            int user1 = Math.min(userId, friendId);
            int user2 = Math.max(userId, friendId);
            
            String sql = "SELECT COUNT(*) FROM Friendship WHERE user_id1 = ? AND user_id2 = ?";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, user1, user2);
            return count != null && count > 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Integer> getFriendIds(int userId) {
        try {
            List<Integer> friendIds = new ArrayList<>();
            
            // Get friends where userId is user_id1
            String sql1 = "SELECT user_id2 FROM Friendship WHERE user_id1 = ?";
            List<Integer> friends1 = jdbcTemplate.queryForList(sql1, Integer.class, userId);
            friendIds.addAll(friends1);
            
            // Get friends where userId is user_id2
            String sql2 = "SELECT user_id1 FROM Friendship WHERE user_id2 = ?";
            List<Integer> friends2 = jdbcTemplate.queryForList(sql2, Integer.class, userId);
            friendIds.addAll(friends2);
            
            return friendIds;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return List.of();
        }
    }
} 