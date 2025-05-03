package com.watchlist.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.watchlist.models.Movie;

@Repository
public class MovieRepositoryImpl implements MovieRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Movie> findAll() {
        String sql = "SELECT * FROM Movie";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Movie.class));
    }

    @Override
    public Optional<Movie> findById(int movieId) {
        String sql = "SELECT * FROM Movie WHERE movie_id = ?";
        List<Movie> movies = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Movie.class), movieId);
        return movies.stream().findFirst();
    }
}
