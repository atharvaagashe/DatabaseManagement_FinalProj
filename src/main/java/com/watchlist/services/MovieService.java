package com.watchlist.services;

import com.watchlist.models.Movie;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
    private final String JDBC_URL = "jdbc:mysql://localhost:33306/moviedb";
    private final String DB_USER = "root";
    private final String DB_PASS = "mysqlpass";

    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM Movie";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Movie m = new Movie();
                m.setMovieId(rs.getInt("movie_id"));
                m.setTitle(rs.getString("title"));
                m.setGenre(rs.getString("genre"));
                m.setReleaseYear(rs.getInt("release_year"));
                m.setTvShow(rs.getBoolean("is_tv_show"));
                movies.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movies;
    }
}
