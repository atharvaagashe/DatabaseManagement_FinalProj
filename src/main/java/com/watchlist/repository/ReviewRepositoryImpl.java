package com.watchlist.repository;

import com.watchlist.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

import com.watchlist.models.Review;

@Repository
public class ReviewRepositoryImpl implements ReviewRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Review> reviewRowMapper = (rs, rowNum) -> {
        Review review = new Review();
        review.setId(rs.getLong("review_id"));
        review.setUserId(rs.getLong("user_id"));
        review.setMovieId(rs.getLong("movie_id"));
        review.setRating(rs.getInt("rating"));
        review.setComment(rs.getString("comment"));
        review.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
        return review;
    };

    @Override
    public List<Review> findByMovieId(Long movieId) {
        String sql = "SELECT * FROM Review WHERE movie_id = ?";
        return jdbcTemplate.query(sql, reviewRowMapper, movieId);
    }


    @Override
    public List<Review> findByUserId(Long userId) {
        String sql = "SELECT * FROM Review WHERE user_id = ?";
        return jdbcTemplate.query(sql, reviewRowMapper, userId);
    }

    @Override
    public void save(Review review) {
        String sql = "INSERT INTO Review (user_id, movie_id, rating, comment, timestamp) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                review.getUserId(),
                review.getMovieId(),
                review.getRating(),
                review.getComment(),
                Timestamp.valueOf(review.getTimestamp()));
    }
}
