package com.watchlist.controllers;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.watchlist.models.Movie;
import com.watchlist.models.User;
import com.watchlist.models.Watchlist;
import com.watchlist.repository.MovieRepository;
import com.watchlist.repository.UserRepository;
import com.watchlist.repository.WatchlistRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/watchlist")
public class WatchlistController {

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    // adding to watchlist
    @PostMapping("/add")
    public String addToWatchlist(@RequestParam("movieId") int movieId, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        User user = userRepository.findByUsername(username);
        if (user != null && !watchlistRepository.existsByUserIdAndMovieId(user.getUserId(), movieId)) {
            watchlistRepository.addToWatchlist(user.getUserId(), movieId);
        }

        return "redirect:/movies";
    }

    // Remove from watchlist
    @PostMapping("/remove")
    public String removeFromWatchlist(@RequestParam("movieId") int movieId, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        User user = userRepository.findByUsername(username);
        if (user != null) {
            watchlistRepository.removeFromWatchlist(user.getUserId(), movieId);
        }

        return "redirect:/watchlist";
    }

    // View all watchlisted movies for the user
    @GetMapping({ "", "/" })
    public String viewWatchlist(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        User user = userRepository.findByUsername(username);
        if (user == null) {
            return "redirect:/login";
        }

        List<Watchlist> entries = watchlistRepository.findByUserId(user.getUserId());
        List<Movie> movies = entries.stream()
            .map(entry -> movieRepository.findById(entry.getMovieId()).orElse(null))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        //int watchlistCount = watchlistRepository.countByUserId(user.getUserId());
        model.addAttribute("movies", movies);
        model.addAttribute("username", username);
        model.addAttribute("watchlistCount", movies.size());
        return "watchlist";
    }
}
