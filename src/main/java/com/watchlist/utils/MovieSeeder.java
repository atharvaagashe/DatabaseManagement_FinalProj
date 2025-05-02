package com.watchlist.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Random;

public class MovieSeeder {

    private static final String JDBC_URL = "jdbc:mysql://localhost:33306/moviedb";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "mysqlpass";

    private static final String[] GENRES = {"Action", "Comedy", "Drama", "Horror", "Sci-Fi", "Romance", "Documentary"};

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "INSERT INTO Movie (title, genre, release_year, is_tv_show) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            Random rand = new Random();
            for (int i = 1; i <= 1000; i++) {
                stmt.setString(1, "Movie Title " + i);
                stmt.setString(2, GENRES[rand.nextInt(GENRES.length)]);
                stmt.setInt(3, rand.nextInt(60) + 1960); // Random year between 1960–2020
                stmt.setBoolean(4, rand.nextBoolean());
                stmt.addBatch();

                if (i % 100 == 0) {
                    stmt.executeBatch(); // Commit every 100 inserts
                }
            }
            stmt.executeBatch(); // Final commit
            System.out.println("Inserted 1000 movies successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
