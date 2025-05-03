package com.watchlist.repository;

import com.watchlist.models.Review;
import java.util.List;

public interface ReviewRepository {
    List<Review> findByMovieId(Long movieId);
    List<Review> findByUserId(Long userId);
    void save(Review review);
}
