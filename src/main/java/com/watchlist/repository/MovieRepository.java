package com.watchlist.repository;

import java.util.List;
import java.util.Optional;

import com.watchlist.models.Movie;

public interface MovieRepository {
    List<Movie> findAll();
    Optional<Movie> findById(int movieId); 
}
