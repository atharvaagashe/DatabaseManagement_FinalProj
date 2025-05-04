package com.watchlist.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.watchlist.models.Watchlist;

@Repository
public class WatchlistRepositoryImpl implements WatchlistRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addToWatchlist(int userId, int movieId) {
        String sql = "INSERT IGNORE INTO Watchlist (user_id, movie_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, movieId);
    }

    @Override
    public List<Watchlist> findByUserId(int userId) {
        String sql = "SELECT * FROM Watchlist WHERE user_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Watchlist entry = new Watchlist();
            entry.setUserId(rs.getInt("user_id"));
            entry.setMovieId(rs.getInt("movie_id"));
            entry.setSavedDate(rs.getString("saved_date"));
            return entry;
        }, userId);
    }

    @Override
    public boolean existsByUserIdAndMovieId(int userId, int movieId) {
        String sql = "SELECT COUNT(*) FROM Watchlist WHERE user_id = ? AND movie_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId, movieId);
        return count != null && count > 0;
    }
    
    @Override
    public void removeFromWatchlist(int userId, int movieId) {
        String sql = "DELETE FROM Watchlist WHERE user_id = ? AND movie_id = ?";
        jdbcTemplate.update(sql, userId, movieId);
    }
}
