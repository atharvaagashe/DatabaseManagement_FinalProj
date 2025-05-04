package com.watchlist.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.watchlist.models.Movie;
import com.watchlist.models.User;
import com.watchlist.models.Watchlist;
import com.watchlist.repository.UserRepository;
import com.watchlist.repository.WatchlistRepository;
import com.watchlist.services.MovieService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        model.addAttribute("username", username);
        return "dashboard";
    }

    @GetMapping("/reviews")
    public String showReviews() {
        return "reviews";
    }


    @GetMapping("/friends")
    public String showFriends() {
        return "friends";
    }

    @GetMapping("/movies")
    public String showMovies(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        User user = userRepository.findByUsername(username);
        if (user == null) {
            return "redirect:/login";
        }

        List<Movie> allMovies = movieService.getAllMovies();
        List<Watchlist> watchlistEntries = watchlistRepository.findByUserId(user.getUserId());

        Set<Integer> watchlistMovieIds = watchlistEntries.stream()
            .map(Watchlist::getMovieId)
            .collect(Collectors.toSet());

        List<Map<String, Object>> moviesWithFlags = allMovies.stream().map(movie -> {
            Map<String, Object> map = new HashMap<>();
            map.put("movieId", movie.getMovieId());
            map.put("title", movie.getTitle());
            map.put("genre", movie.getGenre());
            map.put("releaseYear", movie.getReleaseYear());
            map.put("inWatchlist", watchlistMovieIds.contains(movie.getMovieId()));
            return map;
        }).collect(Collectors.toList());

        model.addAttribute("movies", moviesWithFlags);
        return "movies";
    }
}
