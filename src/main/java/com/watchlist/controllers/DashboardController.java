package com.watchlist.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;


@Controller
public class DashboardController {

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

    @GetMapping("/movies")
    public String manageMovies() {
        return "movies"; // expects movies.mustache
    }

    @GetMapping("/friends")
    public String showFriends() {
        return "friends"; // expects friends.mustache
    }
}
