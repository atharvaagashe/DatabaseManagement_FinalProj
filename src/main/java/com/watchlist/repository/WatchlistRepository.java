package com.watchlist.repository;
import java.util.List;

import com.watchlist.models.Watchlist;

public interface WatchlistRepository {
    void addToWatchlist(int userId, int movieId);
    List<Watchlist> findByUserId(int userId); 
    boolean existsByUserIdAndMovieId(int userId, int movieId); 
    void removeFromWatchlist(int userId, int movieId);
    int countByUserId(int userId);
}