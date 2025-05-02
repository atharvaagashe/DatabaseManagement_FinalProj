package com.watchlist.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.watchlist.models.Movie;
import com.watchlist.services.MovieService;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;


@Controller
public class DashboardController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login"; // fallback if someone hits /home directly
        }
        model.addAttribute("username", username);
        return "dashboard";
    }


    // Placeholder routes if you later want separate pages
    @GetMapping("/reviews")
    public String showReviews() {
        return "reviews"; // expects reviews.mustache
    }

    @GetMapping("/user")
    public String showUserInfo() {
        return "user"; // expects user.mustache
    }

    @GetMapping("/friends")
    public String showFriends() {
        return "friends"; // expects friends.mustache
    }

    @GetMapping("/movies")
    public String showMovies(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "movies"; // will render movies.mustache
    }

}
