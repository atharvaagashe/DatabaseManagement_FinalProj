package com.watchlist.models;

public class Watchlist {
    private int userId;
    private int movieId;

    private String savedDate;

    // Constructors
    public Watchlist() {}

    public Watchlist(int userId, int movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getSavedDate() {
        return savedDate;
    }

    public void setSavedDate(String savedDate) {
        this.savedDate = savedDate;
    }
}
