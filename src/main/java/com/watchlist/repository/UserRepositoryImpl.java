package com.watchlist.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.watchlist.models.User;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT * FROM User WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{username}, new BeanPropertyRowMapper<>(User.class));
    }
    
    @Override
    public List<User> findAllUsers() {
        String sql = "SELECT * FROM User";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }
}
