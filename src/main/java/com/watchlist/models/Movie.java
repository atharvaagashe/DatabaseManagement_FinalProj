package com.watchlist.models;

public class Movie {
    private int movieId;
    private String title;
    private String genre;
    private int releaseYear;
    private boolean isTvShow;

    // Constructors
    public Movie() {}

    public Movie(int movieId, String title, String genre, int releaseYear, boolean isTvShow) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.isTvShow = isTvShow;
    }

    // Getters and Setters
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public boolean isTvShow() {
        return isTvShow;
    }

    public void setTvShow(boolean tvShow) {
        isTvShow = tvShow;
    }
}
